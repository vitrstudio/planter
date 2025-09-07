package studio.vitr.planter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class GithubConfig(
    @Value("\${github.oauth.client-id}")
    val clientId: String,

    @Value("\${github.oauth.client-secret}")
    val clientSecret: String,

    @Value("\${github.oauth.redirect-uri}")
    val redirectUri: String,
)