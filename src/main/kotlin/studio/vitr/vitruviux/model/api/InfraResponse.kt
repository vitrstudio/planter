package studio.vitr.vitruviux.model.api

class InfraResponse(
    val isApiRunning: Boolean,
    val isDatabaseRunning: Boolean,
    val isApplicationBucketCreated: Boolean
)