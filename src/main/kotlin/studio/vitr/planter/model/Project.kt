package studio.vitr.planter.model

import studio.vitr.planter.model.integrations.AwsInfra
import studio.vitr.planter.model.integrations.GithubRepo

data class Project(
    val repo: GithubRepo,
    val infra: AwsInfra
)