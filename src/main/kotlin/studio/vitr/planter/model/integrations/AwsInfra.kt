package studio.vitr.planter.model.integrations

class AwsInfra(
    val isApiRunning: Boolean,
    val isDatabaseRunning: Boolean,
    val isApplicationBucketCreated: Boolean
)