package studio.vitr.planter.model

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
    @Version val version: Int? = null,
    val githubUserId: Long,
    val createdAt: Long = System.currentTimeMillis(),

    @OneToMany(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    val projects: MutableList<Project> = mutableListOf()
)
