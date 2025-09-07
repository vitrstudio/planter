package studio.vitr.planter.integrations

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class GithubOauthClientConfig {


    @Bean
    fun oauthRestClient(builder: RestClient.Builder) = builder
        .baseUrl("https://github.com")
        .defaultHeader("Accept", "application/json")
        .defaultHeader("User-Agent", "vitruviux")
        .build()

    @Bean
    fun oauthGithubClient(@Qualifier("oauthRestClient") restClient: RestClient): GithubOauthClient =
        HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(GithubOauthClient::class.java)
}