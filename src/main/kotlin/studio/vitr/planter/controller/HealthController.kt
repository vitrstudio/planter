package studio.vitr.planter.controller

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
class HealthController(
    @Value("\${spring.application.version}")
    private val appVersion: String
) {

    @GetMapping("/health")
    fun health(): Map<String, Any> {
        val appVersion = appVersion
        return mapOf(
            "status" to "UP",
            "version" to appVersion
        )
    }
}