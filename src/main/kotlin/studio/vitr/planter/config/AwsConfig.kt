package studio.vitr.planter.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AwsConfig(
    @Value("\${aws.region}")
    val region: String,

    @Value("\${aws.profile}")
    val profile: String?,

    @Value("\${aws.control-plane-account-id}")
    val controlPlaneAccountId: String,
)
