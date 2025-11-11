package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.model.api.ProjectResponse
import studio.vitr.planter.model.enums.ProjectType
import studio.vitr.planter.model.integrations.GithubRepo
import studio.vitr.planter.utils.TimeUtil

@Component
class ProjectAdapter {

    fun toProjectResponse(repo: GithubRepo) = ProjectResponse(
        githubRepositoryId = repo.id.toString(),
        name = repo.name,
        type = ProjectType.UNKNOWN,
        createdAt = TimeUtil.now(),
    )
} 