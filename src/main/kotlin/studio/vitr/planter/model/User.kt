package studio.vitr.planter.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.FetchType.LAZY
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,
    @Version val version: Int? = null,
    val githubUserId: Long,
    val createdAt: Long = System.currentTimeMillis(),

    @OneToMany(
        mappedBy = "user",
        fetch = LAZY,
        cascade = [ALL],
        orphanRemoval = true
    )
    @JsonBackReference
    val projects: MutableList<Project> = mutableListOf()
)
