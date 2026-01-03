package studio.vitr.vitruviux.errors

import org.springframework.http.HttpStatus.UNAUTHORIZED

class ExpiredToken(tokenType: String) : ApiException(
    "$tokenType is expired",
    UNAUTHORIZED,
    "TOKEN_EXPIRED"
)