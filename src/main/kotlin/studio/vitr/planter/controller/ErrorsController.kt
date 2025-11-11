package studio.vitr.planter.controller

import jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import studio.vitr.planter.errors.ApiException

@RestController
class ErrorsController : ErrorController {

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): ResponseEntity<Map<String, Any>> = request
        .let { it.getAttribute(ERROR_EXCEPTION) as? Throwable }
        ?.let { getApiExceptionOrNull(it) }
        ?.let { ResponseEntity.status(it.status).body(buildBody(it)) }
        ?: ResponseEntity.status(INTERNAL_SERVER_ERROR).build()

    private fun buildBody(e: ApiException) = mapOf(
        "message" to (e.message ?: e.code),
        "code" to e.code,
        "status" to e.status.value()
    )

    private fun getApiExceptionOrNull(t: Throwable): ApiException? {
        var cur: Throwable? = t
        while (cur != null) {
            if (cur is ApiException) return cur
            cur = cur.cause
        }
        return null
    }
}
