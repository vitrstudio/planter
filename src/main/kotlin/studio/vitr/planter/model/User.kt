package studio.vitr.planter.model

import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = IDENTITY) val id: Long = 0,
    val githubUserId: Long,
    val createdAt: Long = System.currentTimeMillis(),

    @OneToMany(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    val projects: List<Project> = mutableListOf()
)
