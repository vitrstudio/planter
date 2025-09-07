package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.UserAdapter
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.service.UserService
import java.util.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userAdapter: UserAdapter,
) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) = userService.get(userId)
        ?.let { userAdapter.toUserResponse(it) }
        ?: throw NotFound(USER, userId.toString())

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: UUID) = userService.delete(userId)
}