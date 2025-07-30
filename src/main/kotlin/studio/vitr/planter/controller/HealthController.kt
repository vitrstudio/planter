package studio.vitr.planter.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController(
    @Value("\${spring.application.version}")
    private val appVersion: String
) {

    @GetMapping("/api/health")
    fun health(): Map<String, Any> {
        val appVersion = appVersion
        return mapOf(
            "status" to "UP",
            "version" to appVersion
        )
    }
}