package com.codefarm.planter.adapter

import com.codefarm.planter.constants.Properties.ID
import com.codefarm.planter.errors.InvalidParameter
import com.codefarm.planter.model.Project
import com.codefarm.planter.model.api.ProjectRequest
import com.codefarm.planter.model.api.ProjectResponse
import org.springframework.stereotype.Component

@Component
class ProjectAdapter {
    fun toProject(request: ProjectRequest) = Project(
        name = request.name,
        type = request.type,
    )

    fun toProjectResponse(project: Project) = ProjectResponse(
            id = project.id?: throw InvalidParameter(ID),
            name = project.name,
            type = project.type,
    )
} 