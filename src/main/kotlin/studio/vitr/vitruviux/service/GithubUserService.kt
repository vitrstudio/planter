package studio.vitr.vitruviux.service

import studio.vitr.vitruviux.model.db.GithubUser
import studio.vitr.vitruviux.model.integrations.GithubAccount
import studio.vitr.vitruviux.model.integrations.GithubTokenResponse

interface GithubUserService {
    fun get(id: Long): GithubUser?
    fun upsert(account: GithubAccount, tokens: GithubTokenResponse): GithubUser
    fun delete(id: Long)
}