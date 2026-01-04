package studio.vitr.vitruviux.auth

import studio.vitr.vitruviux.model.dto.Session

interface GithubAuthService {
    fun generateAuthUrl(): String
    fun signIn(code: String, state: String): Session
}
