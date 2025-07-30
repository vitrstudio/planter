package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.UserAdapter
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.api.UserRequest
import studio.vitr.planter.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userAdapter: UserAdapter,
) {

    @GetMapping
    fun getUsers() = userService.getAll()
        .map { userAdapter.toUserResponse(it) }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long) = userService.get(userId)
        ?.let { userAdapter.toUserResponse(it) }
        ?: throw NotFound(USER, userId.toString())

    @PostMapping
    fun createUser(@RequestBody request: UserRequest) = userAdapter
        .toUser(request)
        .let { userService.create(it) }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long) = userService.delete(userId)
}