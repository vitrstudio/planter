package studio.vitr.vitruviux.service

import studio.vitr.vitruviux.model.db.User
import studio.vitr.vitruviux.model.integrations.GithubAccount
import java.util.*

interface UserService {
    fun get(id: UUID): User?
    fun getByGithubUserId(id: Long): User?
    fun create(account: GithubAccount): User
    fun setAwsAccountId(id: UUID, awsAccountId: String): User
    fun delete(id: UUID)
}