package studio.vitr.vitruviux.model.api

import studio.vitr.vitruviux.model.enums.ProjectType

class ProjectResponse(
    val githubRepositoryId: String,
    val name: String,
    val type: ProjectType,
    val infra: InfraResponse,
    val createdAt: Long
)