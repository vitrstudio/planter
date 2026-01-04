package studio.vitr.vitruviux.adapter

import org.springframework.stereotype.Component
import studio.vitr.vitruviux.constants.Properties.USER_ID
import studio.vitr.vitruviux.errors.MissingExpectedParameter
import studio.vitr.vitruviux.model.api.UserResponse
import studio.vitr.vitruviux.model.db.GithubUser
import studio.vitr.vitruviux.model.db.User

@Component
class UserAdapter {

    fun toUserResponse(u: User, g: GithubUser, isAwsAccountReady: Boolean) = UserResponse(
        id = u.id ?: throw MissingExpectedParameter(USER_ID),
        githubUserId = u.githubAccountId,
        awsAccountId = u.awsAccountId,
        awsAccountEnabled = isAwsAccountReady,
        name = g.username,
        avatarUrl = g.avatarUrl,
        createdAt = u.createdAt
    )
}