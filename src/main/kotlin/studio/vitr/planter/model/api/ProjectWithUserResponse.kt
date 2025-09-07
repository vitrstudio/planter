package studio.vitr.planter.model.api

import studio.vitr.planter.model.enums.ProjectType
import java.util.*

class ProjectWithUserResponse(
    val id: UUID,
    val name: String,
    val type: ProjectType,
    val user : UserResponse
)
