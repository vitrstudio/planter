package com.codefarm.planter.model.api

import com.codefarm.planter.model.ProjectType

class ProjectRequest(
    val name: String,
    val type: ProjectType,
)