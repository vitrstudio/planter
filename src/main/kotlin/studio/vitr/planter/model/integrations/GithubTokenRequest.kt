package studio.vitr.planter.model.integrations

data class GithubTokenRequest(
    val clientId: String,
    val clientSecret: String,
    val code: String,
    val state: String,
)