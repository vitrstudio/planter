package studio.vitr.planter.model.enums

import studio.vitr.planter.constants.Properties.TOKEN_TYPE
import studio.vitr.planter.errors.InvalidEnumValue

enum class TokenType {
    ACCESS,
    REFRESH;

    companion object {
        fun fromString(value: String) = entries
            .find { it.toString() == value }
            ?: throw InvalidEnumValue(TOKEN_TYPE, value)
    }
}