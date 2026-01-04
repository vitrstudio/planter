package studio.vitr.vitruviux.model.enums

import studio.vitr.vitruviux.constants.Properties.TOKEN_TYPE
import studio.vitr.vitruviux.errors.InvalidEnumValue

enum class TokenType {
    ACCESS,
    REFRESH;

    companion object {
        fun fromString(value: String) = entries
            .find { it.toString() == value }
            ?: throw InvalidEnumValue(TOKEN_TYPE, value)
    }
}