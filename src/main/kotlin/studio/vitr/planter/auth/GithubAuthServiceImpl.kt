package studio.vitr.vitruviux.auth

import org.springframework.stereotype.Service
import studio.vitr.vitruviux.adapter.GithubAdapter
import studio.vitr.vitruviux.config.GithubConfig
import studio.vitr.vitruviux.constants.Constants.BEARER
import studio.vitr.vitruviux.constants.Properties.USER_ID
import studio.vitr.vitruviux.constants.Standards.UTF_8
import studio.vitr.vitruviux.errors.MissingExpectedParameter
import studio.vitr.vitruviux.integrations.github.GithubClient
import studio.vitr.vitruviux.integrations.github.GithubOauthClient
import studio.vitr.vitruviux.model.dto.Session
import studio.vitr.vitruviux.service.GithubUserService
import studio.vitr.vitruviux.service.UserService
import java.net.URLEncoder
import java.util.*

@Service
class GithubAuthServiceImpl(
    private val config: GithubConfig,
    private val oauthClient: GithubOauthClient,
    private val githubClient: GithubClient,
    private val jwtService: JwtService,
    private val userService: UserService,
    private val githubUserService: GithubUserService,
    private val adapter: GithubAdapter,
): GithubAuthService {

    override fun generateAuthUrl() = "https://github.com/login/oauth/authorize?" +
            "client_id=${config.clientId}&" +
            "redirect_uri=${urlEncodeUtf8(config.redirectUri)}&" +
            "scope=${urlEncodeUtf8("public_repo delete_repo read:user user:email")}&" +
            "state=${UUID.randomUUID()}&" +
            "response_type=code"

    override fun signIn(code: String, state: String): Session {
        val tokens = exchangeCodeForTokens(code, state)
        val account = githubClient.getAccount("$BEARER ${tokens.accessToken}")
        val githubUser = githubUserService.upsert(account, tokens)
        val user = userService.getByGithubUserId(githubUser.accountId) ?: userService.create(account)
        val userId = user.id ?: throw MissingExpectedParameter(USER_ID)

        val accessToken = jwtService.generateAccessToken(userId)
        val refreshToken = jwtService.generateRefreshToken(userId)
        return Session(user.id.toString(), accessToken, refreshToken)
    }

    fun exchangeCodeForTokens(code: String, state: String) = adapter
        .toGithubTokenRequest(config, code, state)
        .let { oauthClient.exchangeCodeForTokens(it) }

    private fun urlEncodeUtf8(input: String) = URLEncoder.encode(input, UTF_8)
}
