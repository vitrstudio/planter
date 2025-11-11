package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.constants.Properties
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.GithubCredentials
import studio.vitr.planter.model.integrations.GithubTokenResponse
import studio.vitr.planter.repository.GithubCredentialsRepository
import java.util.*

@Service
class GithubCredentialsServiceImpl(private val repository: GithubCredentialsRepository): GithubCredentialsService {

    override fun upsertGithubCredentials(
        userId: UUID,
        githubUserId: Long,
        tokens: GithubTokenResponse
    ) = repository
        .findByUserId(userId)
        .let { getNewOrUpdatedCredentialsCopy(it, userId, githubUserId, tokens) }
        .let { repository.save(it) }

    override fun deleteUserCredentials(userId: UUID) = repository
        .findByUserId(userId)
        ?.id
        ?.let { repository.deleteById(it) }
        ?: throw NotFound(Properties.GITHUB_CREDENTIALS, userId.toString())

    private fun getNewOrUpdatedCredentialsCopy(
        credentials: GithubCredentials?,
        userId: UUID,
        githubUserId: Long,
        tokens: GithubTokenResponse
    ) = credentials
        ?.let { updatedCredentials(it, tokens) }
        ?: newCredentials(userId, githubUserId, tokens)

    private fun newCredentials(userId: UUID, githubUserId: Long, tokens: GithubTokenResponse) = GithubCredentials(
        userId = userId,
        githubUserId = githubUserId,
        scope = tokens.scope,
        accessToken = tokens.accessToken ?: "", // todo - fix
    )

    private fun updatedCredentials(credentials: GithubCredentials, tokens: GithubTokenResponse) = credentials.copy(
        scope = tokens.scope,
        accessToken = tokens.accessToken ?: "", // todo - fix
    )
}