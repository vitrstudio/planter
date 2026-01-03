package studio.vitr.vitruviux.model

import studio.vitr.vitruviux.model.integrations.AwsInfra
import studio.vitr.vitruviux.model.integrations.GithubRepo

data class Project(
    val repo: GithubRepo,
    val infra: AwsInfra
)