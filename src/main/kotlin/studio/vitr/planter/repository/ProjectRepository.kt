package studio.vitr.planter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import studio.vitr.planter.model.Project
import java.util.UUID

@Repository
interface ProjectRepository : JpaRepository<Project, UUID> {

    @Query("SELECT p FROM Project p WHERE p.user.id = :id")
    fun findByUserId(id: UUID): List<Project>
}