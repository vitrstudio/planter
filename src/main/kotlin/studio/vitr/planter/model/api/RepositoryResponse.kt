package studio.vitr.planter.model.api

import studio.vitr.planter.model.enums.ProjectType

class RepositoryResponse(
    val id: String,
    val name: String,
    val type: ProjectType,
    val createdAt: Long
)