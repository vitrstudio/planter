package studio.vitr.planter.model

import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "projects")
data class Project(
        @Id @GeneratedValue(strategy = IDENTITY) val id: Long = 0,
        val githubRepositoryId: Long,

        @Enumerated(STRING)
        val type: ProjectType,
        val createdAt: Long = System.currentTimeMillis(),

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user: User? = null
)