package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.UserAdapter
import studio.vitr.planter.auth.AwsService
import studio.vitr.planter.constants.Properties.GITHUB_USER
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.api.AwsAccountSetupRequest
import studio.vitr.planter.model.api.UserResponse
import studio.vitr.planter.service.GithubUserService
import studio.vitr.planter.service.UserService
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val githubUserService: GithubUserService,
    private val awsService: AwsService,
    private val userAdapter: UserAdapter,
) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID): UserResponse {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        val githubUser = githubUserService.get(user.githubAccountId) ?: throw NotFound(GITHUB_USER, userId.toString())
        return userAdapter.toUserResponse(user, githubUser)
    }

    @PostMapping("/{userId}/aws")
    fun setupAwsAccount(
        @PathVariable userId: UUID,
        @RequestBody request: AwsAccountSetupRequest
    ) = userService
        .setAwsAccountId(userId, request.accountId)
        .let { githubUserService.get(it.githubAccountId) }
        ?.let { awsService.getAwsAccountSetupUrl(it) }
        ?: throw NotFound(GITHUB_USER, userId.toString())

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        userService.delete(userId)
        githubUserService.delete(user.githubAccountId)
    }
}