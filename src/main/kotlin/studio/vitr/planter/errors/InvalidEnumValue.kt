package studio.vitr.planter.errors

import org.springframework.http.HttpStatus.BAD_REQUEST

class InvalidEnumValue(enumType: String, value: String): ApiException(
    "invalid enum value: $enumType - $value",
    BAD_REQUEST,
    "INVALID_ENUM_VALUE"
)