package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.ID
import studio.vitr.planter.errors.InvalidParameter
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.UserRequest
import studio.vitr.planter.model.api.UserResponse

@Component
class UserAdapter {

    fun toUser(request: UserRequest) = User(
        githubUserId = request.githubUserId
    )

    fun toUserResponse(user: User) = UserResponse(
        id = user.id,
        githubUserId = user.githubUserId
    )
}