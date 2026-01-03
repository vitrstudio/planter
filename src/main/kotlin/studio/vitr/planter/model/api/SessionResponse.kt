package studio.vitr.vitruviux.model.api

data class SessionResponse(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)