package studio.vitr.vitruviux.errors

import org.springframework.http.HttpStatus.BAD_REQUEST

class InvalidParameter(param: String): ApiException(
    "invalid $param",
    BAD_REQUEST,
    "INVALID_PARAMETER"
)