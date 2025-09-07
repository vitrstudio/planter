package studio.vitr.planter.auth

import studio.vitr.planter.model.dto.Session
import java.util.*

interface AuthService {
    fun refresh(refreshToken: String): Session
    fun validateAccessToken(token: String)
    fun getUserId(token: String): UUID
}
