package studio.vitr.planter.model

import studio.vitr.planter.model.enums.TokenType
import studio.vitr.planter.utils.TimeUtil

class SessionToken(
    val type: TokenType,
    val userId: String,
    val email: String?,
    val issuedAt: Long,
    val expiresAt: Long
) {
    fun isExpired() = TimeUtil.now() > expiresAt
}