package studio.vitr.vitruviux.auth

import studio.vitr.vitruviux.model.db.GithubUser

interface AwsService {
    fun isAwsAccountReady(username: String, awsAccountId: String): Boolean
    fun getAwsAccountSetupUrl(user: GithubUser): String
}
