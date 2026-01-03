package studio.vitr.vitruviux.service

import org.springframework.stereotype.Service
import studio.vitr.vitruviux.constants.Properties.GITHUB_USER
import studio.vitr.vitruviux.errors.NotFound
import studio.vitr.vitruviux.model.db.GithubUser
import studio.vitr.vitruviux.model.integrations.GithubAccount
import studio.vitr.vitruviux.model.integrations.GithubTokenResponse
import studio.vitr.vitruviux.repository.GithubUserRepository

@Service
class GithubUserServiceImpl(private val repository: GithubUserRepository): GithubUserService {

    override fun get(id: Long) = repository.findByGithubAccountId(id)

    override fun upsert(
        account: GithubAccount,
        tokens: GithubTokenResponse
    ) = repository
        .findByGithubAccountId(account.id)
        .let { getNewOrUpdatedGithubUser(it, account, tokens) }
        .let { repository.save(it) }

    override fun delete(id: Long) = repository
        .findByGithubAccountId(id)
        ?.let { repository.delete(it.accountId) }
        ?: throw NotFound(GITHUB_USER, id.toString())

    private fun getNewOrUpdatedGithubUser(
        user: GithubUser?,
        account: GithubAccount,
        tokens: GithubTokenResponse
    ) = user
        ?.let { updatedGithubUser(it, account, tokens) }
        ?: newGithubUser(account, tokens)

    private fun newGithubUser(account: GithubAccount, tokens: GithubTokenResponse) = GithubUser(
        id = null,
        accountId = account.id,
        username = account.login,
        email = account.email,
        avatarUrl = account.avatarUrl,
        scope = tokens.scope,
        accessToken = tokens.accessToken ?: "",
    )

    private fun updatedGithubUser(user: GithubUser, account: GithubAccount, tokens: GithubTokenResponse) = user.copy(
        username = account.login,
        email = account.email,
        avatarUrl = account.avatarUrl,
        scope = tokens.scope,
        accessToken = tokens.accessToken ?: "",
    )
}