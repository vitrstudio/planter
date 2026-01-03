package studio.vitr.vitruviux.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.vitruviux.adapter.UserAdapter
import studio.vitr.vitruviux.auth.AwsService
import studio.vitr.vitruviux.constants.Properties.GITHUB_USER
import studio.vitr.vitruviux.constants.Properties.USER
import studio.vitr.vitruviux.errors.NotFound
import studio.vitr.vitruviux.model.api.AwsAccountSetupRequest
import studio.vitr.vitruviux.model.api.UserResponse
import studio.vitr.vitruviux.service.GithubUserService
import studio.vitr.vitruviux.service.UserService
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
        val isAwsAccountReady = user.awsAccountId
            ?.let { awsService.isAwsAccountReady(githubUser.username, it) }
            ?: false

        return userAdapter.toUserResponse(user, githubUser, isAwsAccountReady)
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