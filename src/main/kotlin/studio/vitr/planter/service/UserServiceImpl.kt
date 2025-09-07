package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.constants.Properties
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.User
import studio.vitr.planter.model.enums.AuthProvider
import studio.vitr.planter.model.integrations.GithubUser
import studio.vitr.planter.repository.UserRepository
import studio.vitr.planter.utils.TimeUtil
import java.util.*

@Service
class UserServiceImpl(
    val repository: UserRepository,
    val credentialsService: GithubCredentialsService,
): UserService {

    override fun get(id: UUID): User? = repository
        .findById(id)
        .orElse(null)

    override fun upsertUser(githubUser: GithubUser): User = repository
        .findByGithubUserId(githubUser.id)
        .let{ getNewOrUpdatedUserCopy(it, githubUser) }
        .let { repository.save(it) }

    override fun delete(id: UUID) = get(id)
        ?.let { repository.delete(it) }
        ?.also { credentialsService.deleteUserCredentials(id) }
        ?: throw NotFound(Properties.USER, id.toString())

    private fun getNewOrUpdatedUserCopy(user: User?, githubUser: GithubUser) = user
        ?.let { updatedUser(it, githubUser) }
        ?: newUser(githubUser)

    private fun newUser(u: GithubUser) = User(
        id = null,
        username = u.login,
        githubUserId = u.id,
        email = u.email,
        avatarUrl = u.avatarUrl,
        provider = AuthProvider.GITHUB,
        createdAt = TimeUtil.now()
    )

    private fun updatedUser(user: User, githubUser: GithubUser) = user.copy(
        email = githubUser.email,
        username = githubUser.login,
        avatarUrl = githubUser.avatarUrl,
    )
}