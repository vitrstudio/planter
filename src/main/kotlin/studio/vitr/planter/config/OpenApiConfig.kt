package studio.vitr.vitruviux.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    private val securitySchemeName = "bearerAuth"

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
            .info(info())
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
            .components(components())

    private fun info() = Info()
        .title("Vitruviux API")
        .description("API documentation for Vitruviux application")
        .version("2.0.1")
        .contact(Contact().name("Vitruviux Team"))

    private fun components() = Components().addSecuritySchemes(securitySchemeName, securityScheme())

    private fun securityScheme() = SecurityScheme()
        .name(securitySchemeName)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .description("JWT authentication using Bearer token")
}

