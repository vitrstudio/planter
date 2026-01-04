package studio.vitr.vitruviux.auth

import studio.vitr.vitruviux.model.dto.Session
import java.util.*

interface AuthService {
    fun refresh(refreshToken: String): Session
    fun validateAccessToken(token: String)
    fun getUserId(token: String): UUID
}
