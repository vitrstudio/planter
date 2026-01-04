package studio.vitr.vitruviux.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import studio.vitr.vitruviux.config.JwtConfig
import studio.vitr.vitruviux.constants.Claims.EMAIL
import studio.vitr.vitruviux.constants.Claims.TYPE
import studio.vitr.vitruviux.model.SessionToken
import studio.vitr.vitruviux.model.enums.TokenType
import studio.vitr.vitruviux.model.enums.TokenType.ACCESS
import studio.vitr.vitruviux.model.enums.TokenType.REFRESH
import studio.vitr.vitruviux.utils.TimeUtil
import studio.vitr.vitruviux.utils.TimeUtil.Companion.now
import java.util.*
import kotlin.text.Charsets.UTF_8

@Service
class JwtServiceImpl(
    config: JwtConfig
): JwtService {

    private val signingKey = Keys.hmacShaKeyFor(config.secret.toByteArray(UTF_8))
    private val accessTokenExpirationMilliseconds = 3600_000L // 60 minutes
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
