package studio.vitr.planter.integrations

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import studio.vitr.planter.model.integrations.GithubRepo
import studio.vitr.planter.model.integrations.GithubUser

@HttpExchange(accept = [APPLICATION_JSON_VALUE])
interface GithubClient {

    @GetExchange(
        url = "/user",
        accept = ["application/vnd.github.v3+json"]
    )
    fun getUserInfo(
        @RequestHeader("Authorization") authHeader: String
    ): GithubUser

    @GetExchange(
        url = "/user/repos",
        accept = ["application/vnd.github.v3+json"]
    )
    fun getUserRepos(
        @RequestHeader("Authorization") authHeader: String
    ): List<GithubRepo>

    // todo - implement
    fun createRepo()

    // todo - implement
    fun deleteRepo()
}