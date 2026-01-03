package studio.vitr.vitruviux.errors

import org.springframework.http.HttpStatus

open class ApiException(
    message: String,
    val status: HttpStatus,
    val code: String
) : RuntimeException(message)
