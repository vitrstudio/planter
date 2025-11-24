package studio.vitr.planter.integrations.aws

import org.springframework.stereotype.Component
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ec2.Ec2Client
import software.amazon.awssdk.services.ec2.model.Filter
import software.amazon.awssdk.services.ec2.model.InstanceStateName.RUNNING
import software.amazon.awssdk.services.rds.RdsClient
import software.amazon.awssdk.services.rds.model.DbInstanceNotFoundException
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.NoSuchBucketException
import software.amazon.awssdk.services.s3.model.S3Exception
import studio.vitr.planter.config.AwsConfig

@Component
class AwsClientImpl(
    private val awsConfig: AwsConfig,
) : AwsClient {

    private val region by lazy { Region.of(awsConfig.region) }

    private val ec2Client: Ec2Client by lazy { Ec2Client.builder()
        .credentialsProvider(null)
        .region(region)
        .build()
    }

    private val rdsClient: RdsClient by lazy { RdsClient.builder()
        .credentialsProvider(null)
        .region(region)
        .build()
    }

    private val s3Client: S3Client by lazy { S3Client.builder()
        .credentialsProvider(null)
        .region(region)
        .build()
    }

    override fun isEc2InstanceRunning(name: String) = ec2Client
        .describeInstances { it.filters(nameFilter(name)) }
        .reservations()
        .flatMap { it.instances() }
        .any { it.state().name() == RUNNING }

    override fun isRdsInstanceAvailable(databaseId: String) = try {
        rdsClient.describeDBInstances { it.dbInstanceIdentifier(databaseId) }
            .dbInstances()
            .any { it.dbInstanceStatus().equals("available", ignoreCase = true) }
    } catch (ex: DbInstanceNotFoundException) {
        false
    }

    override fun doesBucketExist(name: String) = try {
        s3Client.headBucket { it.bucket(name) }
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
