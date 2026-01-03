package studio.vitr.vitruviux.auth

import org.springframework.stereotype.Service
import studio.vitr.vitruviux.adapter.AuthAdapter
import studio.vitr.vitruviux.constants.Properties.ACCESS_TOKEN
import studio.vitr.vitruviux.constants.Properties.REFRESH_TOKEN
import studio.vitr.vitruviux.constants.Properties.TOKEN_TYPE
import studio.vitr.vitruviux.constants.Properties.USER
import studio.vitr.vitruviux.constants.Properties.USER_ID
import studio.vitr.vitruviux.errors.ExpiredToken
import studio.vitr.vitruviux.errors.InvalidParameter
import studio.vitr.vitruviux.errors.InvalidPayloadAttribute
import studio.vitr.vitruviux.errors.NotFound
import studio.vitr.vitruviux.model.dto.Session
import studio.vitr.vitruviux.model.enums.TokenType.ACCESS
import studio.vitr.vitruviux.service.UserService
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
