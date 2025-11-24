package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.model.api.InfraResponse
import studio.vitr.planter.model.api.ProjectResponse
import studio.vitr.planter.model.enums.ProjectType
import studio.vitr.planter.model.integrations.AwsInfra
import studio.vitr.planter.model.Project
import studio.vitr.planter.utils.TimeUtil

@Component
class ProjectAdapter {

    fun toProjectResponse(p: Project) = ProjectResponse(
        githubRepositoryId = p.repo.id.toString(),
        name = p.repo.name,
        type = ProjectType.UNKNOWN,
        infra = toInfraResponse(p.infra),
        createdAt = TimeUtil.now(),
    )

    private fun toInfraResponse(i: AwsInfra) = InfraResponse(
        isApiRunning = i.isApiRunning,
        isDatabaseRunning = i.isDatabaseRunning,
        isApplicationBucketCreated = i.isApplicationBucketCreated,
    )
} 