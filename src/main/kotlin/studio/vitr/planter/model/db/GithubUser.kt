package studio.vitr.vitruviux.model.db

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "github_users")
data class GithubUser(
    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID?,
    val accountId: Long,
    val username: String,
    val email: String?,
    val avatarUrl: String,
    val scope: String?,
    val accessToken: String,
)
