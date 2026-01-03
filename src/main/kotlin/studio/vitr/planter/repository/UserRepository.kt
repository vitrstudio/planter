package studio.vitr.vitruviux.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import studio.vitr.vitruviux.model.db.User
import java.util.UUID

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.githubAccountId = :id")
    fun findByGithubAccountId(id: Long): User?
}