package com.codefarm.planter.service

import com.codefarm.planter.adapter.ProjectAdapter
import com.codefarm.planter.integrations.GithubClient
import com.codefarm.planter.model.Project
import com.codefarm.planter.model.User
import com.codefarm.planter.model.api.ProjectRequest
import com.codefarm.planter.repository.ProjectRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProjectService(
    private val projectRepository: ProjectRepository,
    private val githubClient: GithubClient,
    private val adapter: ProjectAdapter,
) {

    fun getAll(): List<Project> = projectRepository.findAll()

    fun getByUserId(userId: UUID) = projectRepository.findByUserId(userId)

    fun create(user: User, request: ProjectRequest) =  githubClient
        .createRepository(user.githubUserId, request.name)
        .let { adapter.toProject(request, user, it) }
        .let { projectRepository.save(it) }

    fun delete(id: UUID) = projectRepository.deleteById(id)
}