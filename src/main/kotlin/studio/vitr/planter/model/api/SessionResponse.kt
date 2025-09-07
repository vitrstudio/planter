package studio.vitr.planter.model.api

data class SessionResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)