package com.codefarm.planter.model.api

import com.codefarm.planter.model.ProjectType
import java.util.UUID

class ProjectResponse(
    val id: UUID,
    val name: String,
    val type: ProjectType
)