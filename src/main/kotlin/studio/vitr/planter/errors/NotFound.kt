package studio.vitr.planter.errors

class NotFound(entity: String, id: String): Error("$entity $id not found")