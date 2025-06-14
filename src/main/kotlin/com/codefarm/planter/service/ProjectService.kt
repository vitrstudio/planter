package com.codefarm.planter.service

import com.codefarm.planter.model.Project
import com.codefarm.planter.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(private val projectRepository: ProjectRepository) {
    
    fun getAll(): List<Project> = projectRepository.findAll()

    fun create(project: Project) = projectRepository.save(project)
} 