package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.ID
import studio.vitr.planter.errors.InvalidParameter
import studio.vitr.planter.deprecated.Project
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.api.RepositoryResponse
import studio.vitr.planter.model.api.ProjectWithUserResponse
import studio.vitr.planter.model.enums.ProjectType
import studio.vitr.planter.model.integrations.GithubRepo
import studio.vitr.planter.utils.TimeUtil

@Component
class ProjectAdapter(
    private val userAdapter: UserAdapter,
) {
    fun toProject(request: ProjectRequest, user: User, githubRepositoryId: Long) = Project(
        name = request.name,
        type = request.type,
        githubRepositoryId = githubRepositoryId,
        user = user
    )

    fun toProjectResponse(repo: GithubRepo) = RepositoryResponse(
        id = repo.id.toString(),
        name = repo.name,
        type = ProjectType.UNKNOWN,
        createdAt = TimeUtil.now(),
    )

    fun toProjectWithUserResponse(project: Project, user: User) = ProjectWithUserResponse(
        id = project.id ?: throw InvalidParameter(ID),
        name = project.name,
        type = project.type,
        user = userAdapter.toUserResponse(user)
    )
} 