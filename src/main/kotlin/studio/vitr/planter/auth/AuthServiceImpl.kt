package studio.vitr.planter.auth

import org.springframework.stereotype.Service
import studio.vitr.planter.adapter.AuthAdapter
import studio.vitr.planter.constants.Properties.ACCESS_TOKEN
import studio.vitr.planter.constants.Properties.REFRESH_TOKEN
import studio.vitr.planter.constants.Properties.TOKEN_TYPE
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.constants.Properties.USER_ID
import studio.vitr.planter.errors.ExpiredToken
import studio.vitr.planter.errors.InvalidParameter
import studio.vitr.planter.errors.InvalidPayloadAttribute
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.dto.Session
import studio.vitr.planter.model.enums.TokenType.ACCESS
import studio.vitr.planter.service.UserService
import java.util.*

@Service
class AuthServiceImpl(
    private val adapter: AuthAdapter,
    private val jwtService: JwtService,
    private val userService: UserService,
): AuthService {

    override fun refresh(refreshToken: String): Session {
        val userId = jwtService.getUserId(refreshToken).let { UUID.fromString(it) }
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        val accessToken = jwtService.generateAccessToken(userId)

        // todo - check if github credentials are still valid
        return adapter.toSession(user, accessToken, refreshToken)
    }

    override fun validateAccessToken(token: String) {
        if (!jwtService.validateToken(token)) throw InvalidParameter(ACCESS_TOKEN)
        val attributes = jwtService.extractSessionTokenAttributes(token)
        if (attributes.type != ACCESS) throw InvalidPayloadAttribute(TOKEN_TYPE, REFRESH_TOKEN)
        if (attributes.isExpired()) throw ExpiredToken(ACCESS_TOKEN)
    }

    override fun getUserId(token: String) = jwtService
        .extractSessionTokenAttributes(token).userId
        .takeIf { it.isNotBlank() }
        ?.let { UUID.fromString(it) }
        ?: throw InvalidPayloadAttribute(USER_ID, ACCESS_TOKEN)
}
