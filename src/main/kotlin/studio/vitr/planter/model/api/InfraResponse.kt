package studio.vitr.planter.model.api

class InfraResponse(
    val isApiRunning: Boolean,
    val isDatabaseRunning: Boolean,
    val isApplicationBucketCreated: Boolean
)