package studio.vitr.planter.model.db

import jakarta.persistence.*
import java.util.UUID
import jakarta.persistence.GenerationType.UUID as UUIDX

@Entity
@Table(name = "github_credentials")
data class GithubCredentials(
    @Id @GeneratedValue(strategy = UUIDX) val id: UUID? = null,
    val userId: UUID,
    val githubUserId: Long,
    val scope: String?,
    val accessToken: String,
)
