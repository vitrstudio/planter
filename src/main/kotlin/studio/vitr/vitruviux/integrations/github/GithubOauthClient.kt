package studio.vitr.vitruviux.integrations.github

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import studio.vitr.vitruviux.model.integrations.GithubTokenRequest
import studio.vitr.vitruviux.model.integrations.GithubTokenResponse

@HttpExchange(accept = [APPLICATION_JSON_VALUE])
interface GithubOauthClient {

    @PostExchange(
        url = "/login/oauth/access_token",
        contentType = APPLICATION_JSON_VALUE,
        accept = [APPLICATION_JSON_VALUE]
    )
    fun exchangeCodeForTokens(
        @RequestBody request: GithubTokenRequest,
    ): GithubTokenResponse
}