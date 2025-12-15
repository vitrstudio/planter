package studio.vitr.planter.auth

import org.springframework.stereotype.Service
import studio.vitr.planter.config.AwsConfig
import studio.vitr.planter.model.db.User

@Service
class AwsServiceImpl(
    private val awsConfig: AwsConfig,
): AwsService {

    override fun getAwsAccountSetupUrl(user: User) = "https://console.aws.amazon.com/cloudformation/home#/stacks/quickcreate" +
            "?templateURL=https://vitruviux-cfn-templates.s3.us-east-1.amazonaws.com/vitruviux-provisioning-role.yml" +
            "&stackName=VitruviuxIntegrationStack" +
            "&param_ExternalId=${user.id}" +
            "&param_ControlPlaneAccountId=${awsConfig.controlPlaneAccountId}"
}
