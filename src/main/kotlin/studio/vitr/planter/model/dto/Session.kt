package studio.vitr.vitruviux.model.dto

data class Session(
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
)