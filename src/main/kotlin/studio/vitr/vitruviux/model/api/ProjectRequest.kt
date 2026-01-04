package studio.vitr.vitruviux.model.api

import studio.vitr.vitruviux.model.enums.ProjectType

class ProjectRequest(
    val name: String,
    val type: ProjectType,
)