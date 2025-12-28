package studio.vitr.planter.integrations.aws

import studio.vitr.planter.model.integrations.GithubRepo

interface AwsClient {
    fun isEc2InstanceRunning(repo: GithubRepo): Boolean
    fun isRdsInstanceAvailable(repo: GithubRepo): Boolean
    fun doesBucketExist(repo: GithubRepo): Boolean
}
