package studio.vitr.planter.errors

class ExpiredToken(tokenType: String) : Error("$tokenType is expired")