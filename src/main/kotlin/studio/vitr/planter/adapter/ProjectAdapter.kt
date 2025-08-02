package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.ID
import studio.vitr.planter.errors.InvalidParameter
import studio.vitr.planter.model.Project
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.api.ProjectResponse

@Component
class ProjectAdapter {
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
} 