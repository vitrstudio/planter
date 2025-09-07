package studio.vitr.planter.model.api

import studio.vitr.planter.model.enums.ProjectType
import java.util.UUID

class ProjectResponse(
    val id: UUID,
    val name: String,
    val type: ProjectType,
    val createdAt: Long
)