package studio.vitr.vitruviux.errors

import org.springframework.http.HttpStatus.BAD_REQUEST

class MissingExpectedParameter(param: String): ApiException(
    "missing expected $param",
    BAD_REQUEST,
    "MISSING_EXPECTED_PARAMETER"
)