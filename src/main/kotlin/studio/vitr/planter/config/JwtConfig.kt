package studio.vitr.planter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class JwtConfig(
    @Value("\${jwt.secret}")
    val secret: String
)