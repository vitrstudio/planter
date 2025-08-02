package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.adapter.ProjectAdapter
import studio.vitr.planter.integrations.GithubClient
import studio.vitr.planter.model.Project
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.repository.ProjectRepository
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