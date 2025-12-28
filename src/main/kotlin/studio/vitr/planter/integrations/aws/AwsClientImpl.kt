package studio.vitr.planter.integrations.aws

import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ec2.Ec2Client
import software.amazon.awssdk.services.ec2.model.Filter
import software.amazon.awssdk.services.ec2.model.InstanceStateName.RUNNING
import software.amazon.awssdk.services.rds.RdsClient
import software.amazon.awssdk.services.rds.model.DbInstanceNotFoundException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.NoSuchBucketException
import software.amazon.awssdk.services.s3.model.S3Exception
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest
import studio.vitr.planter.config.AwsConfig

@Component
class AwsClientImpl(
    private val awsConfig: AwsConfig,
) : AwsClient {

    private val region by lazy { Region.of(awsConfig.region) }

    private val stsClient by lazy { StsClient.builder()
        .region(region)
        .credentialsProvider(baseCredentialsProvider())
        .build()
    }

    private fun baseCredentialsProvider(): AwsCredentialsProvider = awsConfig.profile
        ?.takeIf { it.isNotBlank() }
        ?.let { ProfileCredentialsProvider.create(it) }
        ?: DefaultCredentialsProvider.create()

    private fun observerCredentialsProvider(observerRoleArn: String) = StsAssumeRoleCredentialsProvider.builder()
        .stsClient(stsClient)
        .refreshRequest(assumeRoleRequest(observerRoleArn))
        .build()

    private fun assumeRoleRequest(observerRoleArn: String) = AssumeRoleRequest.builder()
        .roleArn(observerRoleArn)
        .roleSessionName("vitruviux-observer")
        .build()

    private fun ec2Client(observerRoleArn: String) = Ec2Client.builder()
        .credentialsProvider(observerCredentialsProvider(observerRoleArn))
        .region(region)
        .build()

    private fun rdsClient(observerRoleArn: String) = RdsClient.builder()
        .credentialsProvider(observerCredentialsProvider(observerRoleArn))
        .region(region)
        .build()

    private fun s3Client(observerRoleArn: String) = S3Client.builder()
        .credentialsProvider(observerCredentialsProvider(observerRoleArn))
        .region(region)
        .build()

    private fun getObserverRole(username: String) = "arn:aws:iam::${awsConfig.controlPlaneAccountId}:role/VitruviuxObserverRole-$username"

    override fun isEc2InstanceRunning(instanceName: String, username: String) = ec2Client(getObserverRole(username))
        .describeInstances { it.filters(nameFilter(instanceName)) }
        .reservations()
        .flatMap { it.instances() }
        .any { it.state().name() == RUNNING }

    override fun isRdsInstanceAvailable(instanceName: String, username: String) = try {
        val observerRoleArn = getObserverRole(username)
        rdsClient(observerRoleArn).describeDBInstances { it.dbInstanceIdentifier(instanceName) }
            .dbInstances()
            .any { it.dbInstanceStatus().equals("available", ignoreCase = true) }
    } catch (ex: DbInstanceNotFoundException) {
        false
    }

    override fun doesBucketExist(instanceName: String, username: String) = try {
        val observerRoleArn = getObserverRole(username)
        s3Client(observerRoleArn).headBucket { it.bucket(instanceName) }
        true
    } catch (ex: NoSuchBucketException) {
        false
    } catch (ex: S3Exception) {
        ex.takeIf { it.statusCode() == 404 }
            ?.let { return false }
            ?: throw ex
    }

    private fun nameFilter(name: String) = Filter.builder()
        .name("tag:Name")
        .values(name)
        .build()
}
