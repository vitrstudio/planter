package studio.vitr.planter.service

import studio.vitr.planter.model.GithubCredentials
import studio.vitr.planter.model.integrations.GithubTokenResponse
import java.util.*

interface GithubCredentialsService {
    fun upsertGithubCredentials(userId: UUID, githubUserId: Long, tokens: GithubTokenResponse): GithubCredentials
    fun deleteUserCredentials(userId: UUID)
}