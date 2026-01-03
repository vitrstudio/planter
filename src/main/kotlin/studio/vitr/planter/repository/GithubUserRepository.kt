package studio.vitr.vitruviux.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import studio.vitr.vitruviux.model.db.GithubUser
import java.util.*

@Repository
interface GithubUserRepository : JpaRepository<GithubUser, UUID> {

    @Query("SELECT u FROM GithubUser u WHERE u.accountId = :id")
    fun findByGithubAccountId(id: Long): GithubUser?

    @Query("DELETE FROM GithubUser u WHERE u.accountId = :id")
    fun delete(id: Long)
}
