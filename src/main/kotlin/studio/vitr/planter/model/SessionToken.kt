package studio.vitr.vitruviux.model

import studio.vitr.vitruviux.model.enums.TokenType
import studio.vitr.vitruviux.utils.TimeUtil

class SessionToken(
    val type: TokenType,
    val userId: String,
    val email: String?,
    val issuedAt: Long,
    val expiresAt: Long
) {
    fun isExpired() = TimeUtil.now() > expiresAt
}