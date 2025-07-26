package studio.vitr.planter.adapter

import studio.vitr.planter.constants.Properties.ID
import studio.vitr.planter.errors.InvalidParameter
import studio.vitr.planter.model.User
import studio.vitr.planter.model.api.UserRequest
import studio.vitr.planter.model.api.UserResponse
import org.springframework.stereotype.Component

@Component
class UserAdapter {

    fun toUser(request: UserRequest) = User(
        githubUserId = request.githubUserId
    )

    fun toUserResponse(user: User) = UserResponse(
        id = user.id?: throw InvalidParameter(ID),
        githubUserId = user.githubUserId
    )
}