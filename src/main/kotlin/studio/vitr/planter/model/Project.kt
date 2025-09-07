package studio.vitr.planter.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.persistence.EnumType.STRING
import studio.vitr.planter.model.enums.ProjectType
import java.util.*

@Entity
@Table(name = "projects")
data class Project(
        @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
        val name: String,
        val githubRepositoryId: Long,

        @Enumerated(STRING)
        val type: ProjectType,
        val createdAt: Long = System.currentTimeMillis(),

        @ManyToOne
        @JsonBackReference
        @JoinColumn(name = "user_id")
        var user: User
)
