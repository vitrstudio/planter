package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.USER_ID
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.model.db.User
import studio.vitr.planter.model.api.UserResponse

@Component
class UserAdapter {

    fun toUserResponse(u: User) = UserResponse(
        id = u.id ?: throw MissingExpectedParameter(USER_ID),
        githubUserId = u.githubUserId,
        awsAccountId = u.awsAccountId,
        name = u.username,
        avatarUrl = u.avatarUrl,
        createdAt = u.createdAt
    )
}