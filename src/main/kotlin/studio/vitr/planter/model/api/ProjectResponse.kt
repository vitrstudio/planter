package studio.vitr.planter.model.api

import studio.vitr.planter.model.enums.ProjectType

class ProjectResponse(
    val githubRepositoryId: String,
    val name: String,
    val type: ProjectType,
    val infra: InfraResponse,
    val createdAt: Long
)