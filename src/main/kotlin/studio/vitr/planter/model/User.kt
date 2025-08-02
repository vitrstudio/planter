package studio.vitr.planter.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
    val githubUserId: Long,
    val createdAt: Long = System.currentTimeMillis(),

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = [ALL], orphanRemoval = true)
    val projects: List<Project> = mutableListOf()
)
