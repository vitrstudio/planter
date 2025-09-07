package studio.vitr.planter.model.integrations

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

// todo - improve this model
@JsonIgnoreProperties(ignoreUnknown = true) // <— ignore any extra fields
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class) // map snake_case to camelCase
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
