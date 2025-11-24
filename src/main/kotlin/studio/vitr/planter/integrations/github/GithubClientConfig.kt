package studio.vitr.planter.integrations.github

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class GithubClientConfig {

    @Bean
    fun githubRestClient(builder: RestClient.Builder) = builder
        .baseUrl("https://api.github.com")
        .defaultHeader("Accept", "application/vnd.github.v3+json")
        .defaultHeader("User-Agent", "vitruviux") // <-- required
        .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
        .build()

    @Bean
    fun githubClient(@Qualifier("githubRestClient") restClient: RestClient): GithubClient =
        HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(GithubClient::class.java)
}