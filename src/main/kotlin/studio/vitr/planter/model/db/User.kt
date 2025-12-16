package studio.vitr.planter.model.db

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*
import jakarta.persistence.GenerationType.UUID as UUIDX

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = UUIDX) val id: UUID?,
    val githubAccountId: Long,
    val awsAccountId: String?,
    val createdAt: Long,
)
