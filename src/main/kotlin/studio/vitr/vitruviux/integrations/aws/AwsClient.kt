package studio.vitr.vitruviux.integrations.aws

interface AwsClient {
    fun isAwsAccountReady(username: String, awsAccountId: String): Boolean
    fun isEc2InstanceRunning(instanceName: String, username: String, awsAccountId: String): Boolean
    fun isRdsInstanceAvailable(instanceName: String, username: String, awsAccountId: String): Boolean
    fun doesBucketExist(instanceName: String, username: String, awsAccountId: String): Boolean
}
