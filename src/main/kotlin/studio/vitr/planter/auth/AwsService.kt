package studio.vitr.planter.auth

import studio.vitr.planter.model.db.GithubUser

interface AwsService {
    fun getAwsAccountSetupUrl(user: GithubUser): String
}
