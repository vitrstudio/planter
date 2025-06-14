package com.codefarm.planter.service

import com.codefarm.planter.integrations.GithubClient
import com.codefarm.planter.model.Project
import com.codefarm.planter.repository.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val githubClient: GithubClient,
) {
    
    fun getAll(): List<Project> = projectRepository.findAll()

    fun create(project: Project) = project
        .let { githubClient.createRepository(project) }
        .let { projectRepository.save(it) }
} 