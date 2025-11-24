package studio.vitr.planter.integrations.aws

interface AwsClient {
    fun isEc2InstanceRunning(name: String): Boolean
    fun isRdsInstanceAvailable(databaseId: String): Boolean
    fun doesBucketExist(name: String): Boolean
}
