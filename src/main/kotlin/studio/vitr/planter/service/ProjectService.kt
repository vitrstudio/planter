package studio.vitr.planter.service

import org.springframework.stereotype.Service
import studio.vitr.planter.constants.Constants.BEARER
import studio.vitr.planter.constants.Properties.GITHUB_USER
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.integrations.aws.AwsClient
import studio.vitr.planter.integrations.github.GithubClient
import studio.vitr.planter.model.Project
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.integrations.AwsInfra
import studio.vitr.planter.model.integrations.GithubRepo
import studio.vitr.planter.model.integrations.GithubRepoRequest
import studio.vitr.planter.model.integrations.GithubRepoTopicsRequest
import java.util.*

@Service
class ProjectService(
    private val userService: UserService,
    private val githubUserService: GithubUserService,
    private val githubClient: GithubClient,
    private val awsClient: AwsClient,
) {

    private val org = "vitrstudio"
    private val template = "bookstore"

    fun getByUserId(userId: UUID) = userService
        .get(userId)
        ?.let { githubUserService.get(it.githubAccountId) }
        ?.let { githubClient.getUserRepos("$BEARER ${it.accessToken}") }
        ?.filter { it.topics.contains("vitruviux") }
        ?.map { Project(it, getInfra(it)) }
        ?: emptyList()

    fun create(userId: UUID, request: ProjectRequest) {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        val githubUser = githubUserService.get(user.githubAccountId) ?: throw NotFound(GITHUB_USER, userId.toString())
        val repoRequest = GithubRepoRequest(request.name)
        val githubAccessToken = "$BEARER ${githubUser.accessToken}"
        val repo = githubClient.generateRepoFromTemplate(githubAccessToken, repoRequest, org, template)
        val topics = GithubRepoTopicsRequest(listOf("vitruviux"))
        githubClient.setRepoTopics(githubAccessToken,  repo.owner.login, repo.name, topics)
    }

    fun delete(userId: UUID, id: UUID) {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        githubClient.deleteRepo()
    }

    private fun getInfra(repo: GithubRepo) = AwsInfra(
        isApiRunning = awsClient.isEc2InstanceRunning(repo),
        isDatabaseRunning = awsClient.isRdsInstanceAvailable(repo),
        isApplicationBucketCreated =  awsClient.doesBucketExist(repo)
    )
}