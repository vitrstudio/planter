package studio.vitr.planter.auth

import studio.vitr.planter.model.db.GithubUser

interface AwsService {
    fun isAwsAccountReady(username: String, awsAccountId: String): Boolean
    fun getAwsAccountSetupUrl(user: GithubUser): String
}
