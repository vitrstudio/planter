package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.USER_ID
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.UserResponse

@Component
class UserAdapter {

    fun toUserResponse(user: User) = UserResponse(
        id = user.id ?: throw MissingExpectedParameter(USER_ID),
        githubUserId = user.githubUserId,
        createdAt = user.createdAt
    )
}