package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.db.User
import studio.vitr.planter.model.integrations.GithubAccount
import studio.vitr.planter.repository.UserRepository
import studio.vitr.planter.utils.TimeUtil
import java.util.*

@Service
class UserServiceImpl(
    val repository: UserRepository,
): UserService {

    override fun get(id: UUID): User? = repository.findById(id).orElse(null)

    override fun getByGithubUserId(id: Long) = repository.findByGithubAccountId(id)

    override fun create(account: GithubAccount) = newUser(account.id).let { repository.save(it) }

    override fun setAwsAccountId(id: UUID, awsAccountId: String) = get(id)
        ?.copy(awsAccountId = awsAccountId)
        ?.let { repository.save(it) }
        ?: throw NotFound(USER, id.toString())

    override fun delete(id: UUID) = repository.deleteById(id)

    private fun newUser(id: Long) = User(
        id = null,
        githubAccountId = id,
        awsAccountId = null,
        createdAt = TimeUtil.now()
    )
}