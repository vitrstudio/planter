package studio.vitr.planter.errors

class InvalidPayloadAttribute(attributeName: String, objectName: String): Error("invalid $attributeName in $objectName")