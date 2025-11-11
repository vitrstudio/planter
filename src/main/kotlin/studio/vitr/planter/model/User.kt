package studio.vitr.planter.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import studio.vitr.planter.model.enums.AuthProvider
import java.util.*
import jakarta.persistence.GenerationType.UUID as UUIDX

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = UUIDX) val id: UUID?,
    val username: String,
    val githubUserId: Long,
    val email: String,
    val providerAccessToken: String?,
    val avatarUrl: String,
    val createdAt: Long,
    val provider: AuthProvider,
)
