package com.codefarm.planter.model

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
data class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
    val githubUserId: Long,
    val createdAt: Long = System.currentTimeMillis(),

    @OneToMany(
        mappedBy = "user",
        fetch = LAZY,
        cascade = [ALL],
        orphanRemoval = true
    )
    val projects: List<Project> = emptyList(),
)
