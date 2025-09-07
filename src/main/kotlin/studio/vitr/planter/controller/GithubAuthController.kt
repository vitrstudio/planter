package studio.vitr.planter.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.AuthAdapter
import studio.vitr.planter.auth.GithubAuthService
import studio.vitr.planter.model.api.GithubAuthUrlResponse
import studio.vitr.planter.model.api.SigninRequest

@RestController
@RequestMapping("/auth/github")
class GithubAuthController(
    private val adapter: AuthAdapter,
    private val githubAuthService: GithubAuthService
) {

    @GetMapping("/url")
    fun initiateGithubAuth() = githubAuthService
        .generateAuthUrl()
        .let { GithubAuthUrlResponse(it)}
        .let { ResponseEntity.ok(it) }

    @PostMapping("/signin")
    fun signIn(
        @RequestBody request: SigninRequest
    ) = githubAuthService.signIn(request.code, request.state)
        .let { adapter.toSessionResponse(it) }
        .let { ResponseEntity.ok(it) }
}
