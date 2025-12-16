package studio.vitr.planter.service

import studio.vitr.planter.model.db.User
import studio.vitr.planter.model.integrations.GithubAccount
import java.util.*

interface UserService {
    fun get(id: UUID): User?
    fun getByGithubUserId(id: Long): User?
    fun create(account: GithubAccount): User
    fun setAwsAccountId(id: UUID, awsAccountId: String): User
    fun delete(id: UUID)
}