package studio.vitr.planter.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import studio.vitr.planter.adapter.AuthAdapter
import studio.vitr.planter.auth.AuthService
import studio.vitr.planter.model.api.RefreshTokenRequest

@RestController
@RequestMapping("/auth")
class AuthController(
    private val adapter: AuthAdapter,
    private val authService: AuthService,
) {

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody request: RefreshTokenRequest
    ) = authService.refresh(request.token)
        .let { adapter.toSessionResponse(it) }
        .let { ResponseEntity.ok(it) }
}
