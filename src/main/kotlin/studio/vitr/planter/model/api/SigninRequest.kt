package studio.vitr.planter.model.api

data class SigninRequest(
    val code: String,
    val state: String
)