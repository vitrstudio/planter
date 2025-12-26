package studio.vitr.planter.auth

import org.springframework.stereotype.Service
import studio.vitr.planter.model.db.GithubUser

@Service
class AwsServiceImpl: AwsService {

    override fun getAwsAccountSetupUrl(user: GithubUser) = buildString {
        append("https://console.aws.amazon.com/cloudformation/home#/stacks/quickcreate")
        append("?stackName=VitruviuxIntegrationStack")
        append("&templateURL=https://vitruviux-cfn-templates.s3.us-east-1.amazonaws.com/vitruviux-provisioning-role.yml")
        append("&param_GitHubOwner=${user.username}")
        append("&param_GitHubEnvironment=vitruviux-prod")
    }
}
