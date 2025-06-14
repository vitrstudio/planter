package com.codefarm.planter.service

import com.codefarm.planter.constants.Properties.USER
import com.codefarm.planter.errors.NotFound
import com.codefarm.planter.model.User
import com.codefarm.planter.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository) {

    fun getAll(): List<User> = userRepository.findAll()

    fun get(id: UUID): User? = userRepository
        .findById(id)
        .orElse(null)

    fun create(user: User) = userRepository.save(user)

    fun delete(userId: UUID) = get(userId)
        ?.let { userRepository.delete(it) }
        ?: throw NotFound(USER, userId.toString())
}