package studio.vitr.vitruviux.adapter

import org.springframework.stereotype.Component
import studio.vitr.vitruviux.config.GithubConfig
import studio.vitr.vitruviux.model.integrations.GithubTokenRequest

@Component
class GithubAdapter {

    fun toGithubTokenRequest(config: GithubConfig, code: String, state: String) = GithubTokenRequest(
        clientId = config.clientId,
        clientSecret = config.clientSecret,
        code = code,
        state = state
    )
}