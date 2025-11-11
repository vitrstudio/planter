package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.constants.Constants.BEARER
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.integrations.GithubClient
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.integrations.GithubRepoRequest
import java.util.*

@Service
class ProjectService(
    private val userService: UserService,
    private val githubClient: GithubClient,
) {

    fun getByUserId(userId: UUID) = userService
        .get(userId)
        ?.let { githubClient.getUserRepos("$BEARER ${it.providerAccessToken}") }
        ?.filter { it.topics.contains("vitruviux") }
        ?: emptyList()

    fun create(userId: UUID, request: ProjectRequest) {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        val repo = GithubRepoRequest(request.name)
        val githubAccessToken = "$BEARER ${user.providerAccessToken}"
        githubClient.createRepo(githubAccessToken, repo)
    }

    fun delete(userId: UUID, id: UUID) {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        githubClient.deleteRepo()
    }
}