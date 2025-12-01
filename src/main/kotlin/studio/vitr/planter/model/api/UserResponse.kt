package studio.vitr.planter.model.api

import java.util.*

class UserResponse(
    val id: UUID,
    val githubUserId: Long,
    val awsAccountId: String?,
    val name: String,
    val avatarUrl: String,
    val createdAt: Long
)