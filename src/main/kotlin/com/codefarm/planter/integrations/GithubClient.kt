package com.codefarm.planter.integrations

import org.springframework.stereotype.Service

@Service
class GithubClient {

    fun createRepository(githubUserId: Long, repositoryName: String) = 1L // Returns a mock repository ID
}