package com.codefarm.planter.adapter

import com.codefarm.planter.constants.Properties.ID
import com.codefarm.planter.errors.InvalidParameter
import com.codefarm.planter.model.User
import com.codefarm.planter.model.api.UserRequest
import com.codefarm.planter.model.api.UserResponse
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