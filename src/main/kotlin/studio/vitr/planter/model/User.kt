package studio.vitr.planter.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.constants.Properties.USER_ID
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
) {
    fun idStr() = id?.toString() ?: throw MissingExpectedParameter(USER_ID)
}
