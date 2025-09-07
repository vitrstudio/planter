package studio.vitr.planter.model.dto

data class Session(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)