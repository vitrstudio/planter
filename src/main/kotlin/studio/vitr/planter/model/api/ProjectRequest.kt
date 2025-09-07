package studio.vitr.planter.model.api

import studio.vitr.planter.model.enums.ProjectType

class ProjectRequest(
    val name: String,
    val type: ProjectType,
)