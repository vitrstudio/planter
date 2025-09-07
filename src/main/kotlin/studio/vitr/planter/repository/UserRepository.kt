package studio.vitr.planter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import studio.vitr.planter.model.User
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.githubUserId = :id")
    fun findByGithubUserId(id: Long): User?
}