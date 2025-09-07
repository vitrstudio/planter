package studio.vitr.planter.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import studio.vitr.planter.config.JwtConfig
import studio.vitr.planter.constants.Claims.EMAIL
import studio.vitr.planter.constants.Claims.TYPE
import studio.vitr.planter.model.SessionToken
import studio.vitr.planter.model.enums.TokenType
import studio.vitr.planter.model.enums.TokenType.ACCESS
import studio.vitr.planter.model.enums.TokenType.REFRESH
import studio.vitr.planter.utils.TimeUtil
import studio.vitr.planter.utils.TimeUtil.Companion.now
import java.util.*
import kotlin.text.Charsets.UTF_8

@Service
class JwtServiceImpl(
    config: JwtConfig
): JwtService {

    private val signingKey = Keys.hmacShaKeyFor(config.secret.toByteArray(UTF_8))
    private val accessTokenExpirationMilliseconds = 900_000L // 15 minutes
    private val refreshTokenExpirationMilliseconds = 2_592_000_000 // 30 days

    override fun generateAccessToken(userId: UUID) = generateToken(userId, ACCESS, accessTokenExpirationMilliseconds)

    override fun generateRefreshToken(userId: UUID) = generateToken(userId, REFRESH, refreshTokenExpirationMilliseconds)

    override fun validateToken(token: String) = runCatching { getClaims(token) }.isSuccess

    override fun extractSessionTokenAttributes(token: String) = sessionToken(getClaims(token))

    override fun getUserId(token: String): String = getClaims(token).subject

    private fun generateToken(userId: UUID, type: TokenType, expiration: Long): String = now().let {
        Jwts.builder()
            .subject(userId.toString())
            .claim(TYPE, type.toString())
            .issuedAt(Date(it))
            .expiration(exp(it, expiration))
            .signWith(signingKey)
            .compact()
    }

    private fun getClaims(token: String) = Jwts.parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .payload

    fun sessionToken(claims: Claims) = SessionToken(
        userId = claims.subject,
        email = claims.get(EMAIL, String::class.java),
        type = claims.get(TYPE, String::class.java).let { TokenType.fromString(it) },
        issuedAt = claims.issuedAt.time,
        expiresAt = claims.expiration.time
    )

    private fun exp(t: Long, duration: Long) = TimeUtil.getExpirationDate(t, duration)
}
