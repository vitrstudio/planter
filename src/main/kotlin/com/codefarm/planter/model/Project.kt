package com.codefarm.planter.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Project(
        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        @Version val version: Int? = null,
        val name: String,
        val type: ProjectType,
)