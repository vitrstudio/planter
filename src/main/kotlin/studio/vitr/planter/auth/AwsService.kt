package studio.vitr.planter.auth

import studio.vitr.planter.model.db.User

interface AwsService {
    fun getAwsAccountSetupUrl(user: User): String
}
