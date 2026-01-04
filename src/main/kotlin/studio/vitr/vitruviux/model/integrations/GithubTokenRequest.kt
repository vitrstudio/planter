package studio.vitr.vitruviux.model.integrations

data class GithubTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val code: String,
    val state: String,
)