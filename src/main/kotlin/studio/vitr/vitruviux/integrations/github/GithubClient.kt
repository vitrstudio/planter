package studio.vitr.vitruviux.integrations.github

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange
import org.springframework.web.service.annotation.PutExchange
import studio.vitr.vitruviux.model.integrations.GithubRepo
import studio.vitr.vitruviux.model.integrations.GithubRepoRequest
import studio.vitr.vitruviux.model.integrations.GithubRepoTopicsRequest
import studio.vitr.vitruviux.model.integrations.GithubAccount

@HttpExchange(accept = [APPLICATION_JSON_VALUE])
interface GithubClient {

    @GetExchange(
        url = "/user",
        accept = ["application/vnd.github.v3+json"]
    )
    fun getAccount(
        @RequestHeader("Authorization") authHeader: String
    ): GithubAccount

    @GetExchange(
        url = "/user/repos?per_page=100",
        accept = ["application/vnd.github.v3+json"]
    )
    fun getUserRepos(
        @RequestHeader("Authorization") authHeader: String
    ): List<GithubRepo>

    @PostExchange(
        url = "/user/repos",
        contentType = APPLICATION_JSON_VALUE,
        accept = ["application/vnd.github.v3+json"]
    )
    fun createRepo(
        @RequestHeader("Authorization") authHeader: String,
        @RequestBody body: GithubRepoRequest
    ): GithubRepo

    @PostExchange(
        url = "/repos/{organisation}/{template}/generate",
        contentType = APPLICATION_JSON_VALUE,
        accept = ["application/vnd.github.baptiste-preview+json"]
    )
    fun generateRepoFromTemplate(
        @RequestHeader("Authorization") authHeader: String,
        @RequestBody body: GithubRepoRequest,
        @PathVariable organisation: String,
        @PathVariable template: String
    ): GithubRepo

    @PutExchange(
        url = "/repos/{owner}/{repo}/topics",
        contentType = APPLICATION_JSON_VALUE,
        accept = ["application/vnd.github+json"],
        headers = ["X-GitHub-Api-Version=2022-11-28"]
    )
    fun setRepoTopics(
        @RequestHeader("Authorization") authHeader: String,
        @PathVariable owner: String,
        @PathVariable repo: String,
        @RequestBody body: GithubRepoTopicsRequest
    )


    // todo - implement
    fun deleteRepo()
}