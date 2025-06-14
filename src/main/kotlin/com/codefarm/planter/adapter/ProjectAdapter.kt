package com.codefarm.planter.adapter

import com.codefarm.planter.constants.Properties.ID
import com.codefarm.planter.errors.InvalidParameter
import com.codefarm.planter.model.Project
import com.codefarm.planter.model.User
import com.codefarm.planter.model.api.ProjectRequest
import com.codefarm.planter.model.api.ProjectResponse
import org.springframework.stereotype.Component

@Component
class ProjectAdapter {
    fun toProject(request: ProjectRequest, user: User, githubRepositoryId: Long) = Project(
        name = request.name,
        type = request.type,
        githubRepositoryId = githubRepositoryId,
        user = user
    )

    fun toProjectResponse(project: Project) = ProjectResponse(
            id = project.id?: throw InvalidParameter(ID),
            name = project.name,
            type = project.type,
    )
} 