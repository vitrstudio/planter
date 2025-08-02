package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.User
import studio.vitr.planter.repository.UserRepository
import java.util.UUID

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