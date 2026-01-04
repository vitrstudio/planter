package studio.vitr.vitruviux.errors

import org.springframework.http.HttpStatus.NOT_FOUND

class NotFound(entity: String, id: String): ApiException(
    "$entity $id not found",
    NOT_FOUND,
    "NOT_FOUND"
)