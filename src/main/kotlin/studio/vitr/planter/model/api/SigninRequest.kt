package studio.vitr.vitruviux.model.api

data class SigninRequest(
    val code: String,
    val state: String
)