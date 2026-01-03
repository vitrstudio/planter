package studio.vitr.vitruviux.model.integrations

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

// todo - improve this model
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GithubTokenResponse(
    // success shape
    val accessToken: String? = null,
    val tokenType: String? = null,
    val scope: String? = null,

    // error shape
    val error: String? = null,
    val errorDescription: String? = null,
    val errorUri: String? = null
)
