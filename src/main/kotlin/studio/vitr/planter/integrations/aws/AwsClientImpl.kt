package studio.vitr.planter.integrations.aws

import org.springframework.stereotype.Component
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
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
import studio.vitr.planter.model.integrations.GithubRepo

@Component
class AwsClientImpl(
    private val awsConfig: AwsConfig,
) : AwsClient {

    private val region by lazy { Region.of(awsConfig.region) }
    private val projectIdPrefix = "vx-"

    private val stsClient by lazy { StsClient.builder()
        .region(region)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .build()
    }

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

    override fun isEc2InstanceRunning(repo: GithubRepo) = ec2Client(getObserverRole(repo.owner.login))
        .describeInstances { it.filters(nameFilter("${repo.name}-api")) }
        .reservations()
        .flatMap { it.instances() }
        .any { it.state().name() == RUNNING }

    override fun isRdsInstanceAvailable(repo: GithubRepo) = try {
        val projectId = getProjectId(repo.topics)
        val observerRoleArn = getObserverRole(repo.owner.login)
        rdsClient(observerRoleArn).describeDBInstances { it.dbInstanceIdentifier("${repo.name}-rds-$projectId") }
            .dbInstances()
            .any { it.dbInstanceStatus().equals("available", ignoreCase = true) }
    } catch (ex: DbInstanceNotFoundException) {
        false
    }

    override fun doesBucketExist(repo: GithubRepo) = try {
        val projectId = getProjectId(repo.topics)
        val observerRoleArn = getObserverRole(repo.owner.login)
        s3Client(observerRoleArn).headBucket { it.bucket("${repo.name}-app-$projectId") }
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

    private fun getProjectId(topics: List<String>) = topics
        .firstOrNull { it.startsWith(projectIdPrefix) }
        ?.removePrefix(projectIdPrefix)
        ?: "unknown"
}
