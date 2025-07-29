package studio.vitr.planter.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import studio.vitr.planter.model.Project

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.user.id = :id")
    fun findByUserId(id: Long): List<Project>
}