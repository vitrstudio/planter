package studio.vitr.vitruviux.adapter

import org.springframework.stereotype.Component
import studio.vitr.vitruviux.model.api.InfraResponse
import studio.vitr.vitruviux.model.api.ProjectResponse
import studio.vitr.vitruviux.model.enums.ProjectType
import studio.vitr.vitruviux.model.integrations.AwsInfra
import studio.vitr.vitruviux.model.Project
import studio.vitr.vitruviux.utils.TimeUtil

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