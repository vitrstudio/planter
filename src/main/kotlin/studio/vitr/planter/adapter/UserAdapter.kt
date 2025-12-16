package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.constants.Properties.USER_ID
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.model.api.UserResponse
import studio.vitr.planter.model.db.GithubUser
import studio.vitr.planter.model.db.User

@Component
class UserAdapter {

    fun toUserResponse(u: User, g: GithubUser) = UserResponse(
        id = u.id ?: throw MissingExpectedParameter(USER_ID),
        githubUserId = u.githubAccountId,
        awsAccountId = u.awsAccountId,
        name = g.username,
        avatarUrl = g.avatarUrl,
        createdAt = u.createdAt
    )
}