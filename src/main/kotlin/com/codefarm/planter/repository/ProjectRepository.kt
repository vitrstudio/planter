package com.codefarm.planter.repository

import com.codefarm.planter.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProjectRepository : JpaRepository<Project, UUID>