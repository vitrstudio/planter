package studio.vitr.planter.service

import studio.vitr.planter.model.User
import studio.vitr.planter.model.integrations.GithubTokenResponse
import studio.vitr.planter.model.integrations.GithubUser
import java.util.*

interface UserService {
    fun get(id: UUID): User?
    fun upsertUser(githubUser: GithubUser, githubTokens: GithubTokenResponse): User
    fun setAwsAccountId(id: UUID, awsAccountId: String): User
    fun delete(id: UUID)
}