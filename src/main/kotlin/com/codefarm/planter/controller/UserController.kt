package com.codefarm.planter.controller

import com.codefarm.planter.adapter.UserAdapter
import com.codefarm.planter.constants.Properties.USER
import com.codefarm.planter.errors.NotFound
import com.codefarm.planter.model.api.UserRequest
import com.codefarm.planter.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val userAdapter: UserAdapter,
) {

    @GetMapping
    fun getUsers() = userService.getAll()
        .map { userAdapter.toUserResponse(it) }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: UUID) = userService.get(userId)
        ?.let { userAdapter.toUserResponse(it) }
        ?: throw NotFound(USER, userId.toString())

    @PostMapping
    fun createUser(@RequestBody request: UserRequest) = userAdapter
        .toUser(request)
        .let { userService.create(it) }
}