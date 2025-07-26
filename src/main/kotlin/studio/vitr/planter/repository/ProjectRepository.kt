package studio.vitr.planter.repository

import studio.vitr.planter.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProjectRepository : JpaRepository<Project, UUID> {
    fun findByUserId(userId: UUID): List<Project>
}