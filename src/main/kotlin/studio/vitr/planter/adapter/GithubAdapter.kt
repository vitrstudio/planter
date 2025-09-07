package studio.vitr.planter.adapter

import org.springframework.stereotype.Component
import studio.vitr.planter.config.GithubConfig
import studio.vitr.planter.model.integrations.GithubTokenRequest

@Component
class GithubAdapter {

    fun toGithubTokenRequest(config: GithubConfig, code: String, state: String) = GithubTokenRequest(
        clientId = config.clientId,
        clientSecret = config.clientSecret,
        code = code,
        state = state
    )
}