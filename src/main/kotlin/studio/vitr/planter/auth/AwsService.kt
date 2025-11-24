package studio.vitr.planter.auth

import studio.vitr.planter.model.User

interface AwsService {
    fun getAwsAccountSetupUrl(user: User): String
}
