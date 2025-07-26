package studio.vitr.planter.model.api

import studio.vitr.planter.model.ProjectType

class ProjectRequest(
    val name: String,
    val type: ProjectType,
)