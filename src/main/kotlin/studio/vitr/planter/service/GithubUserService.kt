package studio.vitr.planter.service

import studio.vitr.planter.model.db.GithubUser
import studio.vitr.planter.model.integrations.GithubAccount
import studio.vitr.planter.model.integrations.GithubTokenResponse

interface GithubUserService {
    fun get(id: Long): GithubUser?
    fun upsert(account: GithubAccount, tokens: GithubTokenResponse): GithubUser
    fun delete(id: Long)
}