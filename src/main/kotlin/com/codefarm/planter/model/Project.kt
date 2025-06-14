package com.codefarm.planter.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Version
import java.util.*

data class Project(
        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        @Version val version: Int? = null,
        val name: String,
        val type: ProjectType,
)