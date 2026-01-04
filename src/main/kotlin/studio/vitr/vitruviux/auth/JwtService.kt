package studio.vitr.vitruviux.auth

import studio.vitr.vitruviux.model.SessionToken
import java.util.*

interface JwtService {
    fun generateAccessToken(userId: UUID): String
    fun generateRefreshToken(userId: UUID): String
    fun getUserId(token: String): String
    fun validateToken(token: String): Boolean
    fun extractSessionTokenAttributes(token: String): SessionToken
}
