package studio.vitr.planter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import studio.vitr.planter.model.db.GithubCredentials
import java.util.*

@Repository
interface GithubCredentialsRepository : JpaRepository<GithubCredentials, UUID> {

    @Query("SELECT c FROM GithubCredentials c WHERE c.userId = :id")
    fun findByUserId(id: UUID): GithubCredentials?
}
