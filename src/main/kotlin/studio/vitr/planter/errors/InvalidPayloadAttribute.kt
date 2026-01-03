package studio.vitr.vitruviux.errors

import org.springframework.http.HttpStatus.BAD_REQUEST

class InvalidPayloadAttribute(attributeName: String, objectName: String): ApiException(
    "invalid $attributeName in $objectName",
    BAD_REQUEST,
    "INVALID_PAYLOAD_ATTRIBUTE"
)