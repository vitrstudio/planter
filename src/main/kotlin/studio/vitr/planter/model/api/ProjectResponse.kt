package studio.vitr.planter.model.api

import studio.vitr.planter.model.ProjectType

class ProjectResponse(
    val id: Long,
    val name: String,
    val type: ProjectType
)