package studio.vitr.planter.auth

import org.springframework.stereotype.Service
import studio.vitr.planter.adapter.GithubAdapter
import studio.vitr.planter.config.GithubConfig
import studio.vitr.planter.constants.Constants.BEARER
import studio.vitr.planter.constants.Properties
import studio.vitr.planter.constants.Standards.UTF_8
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.integrations.github.GithubClient
import studio.vitr.planter.integrations.github.GithubOauthClient
import studio.vitr.planter.model.dto.Session
import studio.vitr.planter.service.GithubCredentialsService
import studio.vitr.planter.service.UserService
import java.net.URLEncoder
import java.util.*

@Service
class GithubAuthServiceImpl(
    private val config: GithubConfig,
    private val oauthClient: GithubOauthClient,
    private val githubClient: GithubClient,
    private val jwtService: JwtService,
    private val userService: UserService,
    private val credentialsService: GithubCredentialsService,
    private val adapter: GithubAdapter,
): GithubAuthService {

    override fun generateAuthUrl() = "https://github.com/login/oauth/authorize?" +
            "client_id=${config.clientId}&" +
            "redirect_uri=${urlEncodeUtf8(config.redirectUri)}&" +
            "scope=${urlEncodeUtf8("public_repo delete_repo read:user user:email")}&" +
            "state=${UUID.randomUUID()}&" +
            "response_type=code"

    override fun signIn(code: String, state: String): Session {
        val githubTokens = exchangeCodeForTokens(code, state)
        val githubUser = githubClient.getUserInfo("$BEARER ${githubTokens.accessToken}")
        val user = userService.upsertUser(githubUser, githubTokens)
        val userId = user.id ?: throw MissingExpectedParameter(Properties.USER_ID)
        credentialsService.upsertGithubCredentials(userId, githubUser.id, githubTokens)

        val accessToken = jwtService.generateAccessToken(userId)
        val refreshToken = jwtService.generateRefreshToken(userId)
        return Session(user.id.toString(), accessToken, refreshToken)
    }

    fun exchangeCodeForTokens(code: String, state: String) = adapter
        .toGithubTokenRequest(config, code, state)
        .let { oauthClient.exchangeCodeForTokens(it) }

    private fun urlEncodeUtf8(input: String) = URLEncoder.encode(input, UTF_8)
}
