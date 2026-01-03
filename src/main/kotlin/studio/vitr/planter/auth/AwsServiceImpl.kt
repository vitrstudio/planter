package studio.vitr.vitruviux.auth

import org.springframework.stereotype.Service
import studio.vitr.vitruviux.config.AwsConfig
import studio.vitr.vitruviux.integrations.aws.AwsClient
import studio.vitr.vitruviux.model.db.GithubUser

@Service
class AwsServiceImpl(
    private val awsConfig: AwsConfig,
    private val awsClient: AwsClient,
) : AwsService {

    override fun isAwsAccountReady(username: String, awsAccountId: String) = awsClient.isAwsAccountReady(username, awsAccountId)

    override fun getAwsAccountSetupUrl(user: GithubUser) = buildString {
        append("https://console.aws.amazon.com/cloudformation/home#/stacks/quickcreate")
        append("?stackName=VitruviuxIntegrationStack-${user.id}")
        append("&templateURL=https://vitruviux-cfn-templates.s3.us-east-1.amazonaws.com/vitruviux-cfn-roles.yml")
        append("&param_GitHubOwner=${user.username}")
        append("&param_GitHubEnvironment=vitruviux-prod")
        append("&param_ControlPlaneAccountId=${awsConfig.controlPlaneAccountId}")
    }
}
