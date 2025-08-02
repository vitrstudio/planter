package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.ID
import studio.vitr.planter.errors.InvalidParameter
import studio.vitr.planter.model.Project
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.api.ProjectResponse
import studio.vitr.planter.model.api.ProjectWithUserResponse

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

    fun toProjectResponse(project: Project) = ProjectResponse(
            id = project.id ?: throw InvalidParameter(ID),
            name = project.name,
            type = project.type,
    )

    fun toProjectWithUserResponse(project: Project, user: User) = ProjectWithUserResponse(
        id = project.id ?: throw InvalidParameter(ID),
        name = project.name,
        type = project.type,
        user = userAdapter.toUserResponse(user)
    )
} 