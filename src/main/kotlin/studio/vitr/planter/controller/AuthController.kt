package studio.vitr.vitruviux.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import studio.vitr.vitruviux.adapter.AuthAdapter
import studio.vitr.vitruviux.auth.AuthService
import studio.vitr.vitruviux.model.api.RefreshTokenRequest

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
