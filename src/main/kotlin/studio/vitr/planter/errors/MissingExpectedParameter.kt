package studio.vitr.planter.errors

class MissingExpectedParameter(param: String): Error("missing expected $param")