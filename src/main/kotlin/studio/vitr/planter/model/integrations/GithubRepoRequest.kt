package studio.vitr.planter.model.integrations

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GithubRepoRequest(
    val name: String,
    val description: String?,
    val private: Boolean,
    val autoInit: Boolean,
    val hasIssues: Boolean,
    val hasProjects: Boolean,
    val hasWiki: Boolean,
) {

    // add secondary constructor
    constructor(name: String): this(
        name = name,
        description = "",
        private = false,
        autoInit = false,
        hasIssues = false,
        hasProjects = false,
        hasWiki = false
    )
}