package studio.vitr.planter.auth

import studio.vitr.planter.model.dto.Session

interface GithubAuthService {
    fun generateAuthUrl(): String
    fun signIn(code: String, state: String): Session
}
