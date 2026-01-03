package studio.vitr.vitruviux.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.filter.OncePerRequestFilter
import studio.vitr.vitruviux.service.UserService
import studio.vitr.vitruviux.constants.Authorities.USER
import studio.vitr.vitruviux.constants.Constants.BEARER
import studio.vitr.vitruviux.constants.Headers.AUTHORIZATION
import studio.vitr.vitruviux.constants.Properties.ACCESS_TOKEN
import studio.vitr.vitruviux.errors.InvalidParameter
import studio.vitr.vitruviux.model.db.GithubUser
import studio.vitr.vitruviux.service.GithubUserService
import java.util.UUID

class JwtAuthenticationFilter(
    private val authenticationService: AuthService,
    private val userService: UserService,
    private val githubUserService: GithubUserService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        authenticateUser(req)
        chain.doFilter(req, res)
    }

    private fun authenticateUser(request: HttpServletRequest) = request.getHeader(AUTHORIZATION)
        ?.takeIf { it.startsWith(BEARER) }
        ?.substring(BEARER.length)
        ?.also { authenticationService.validateAccessToken(it) }
        ?.let { authenticationService.getUserId(it) }
        ?.let { userService.get(it) }
        ?.let { githubUserService.get(it.githubAccountId) }
        ?.let { userDetails(it) }
        ?.let { UsernamePasswordAuthenticationToken(it, null, it.authorities) }
        ?.also { SecurityContextHolder.getContext().authentication = it }
        ?: throw InvalidParameter(ACCESS_TOKEN)

    private fun userDetails(u: GithubUser) = User.builder()
        .username(u.username)
        .password(UUID.randomUUID().toString())
        .authorities(USER)
        .build()
}
