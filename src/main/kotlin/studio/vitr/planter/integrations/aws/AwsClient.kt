package studio.vitr.planter.integrations.aws

interface AwsClient {
    fun isAwsAccountReady(username: String): Boolean
    fun isEc2InstanceRunning(instanceName: String, username: String): Boolean
    fun isRdsInstanceAvailable(instanceName: String, username: String): Boolean
    fun doesBucketExist(instanceName: String, username: String): Boolean
}
