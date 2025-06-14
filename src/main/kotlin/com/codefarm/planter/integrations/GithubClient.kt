package com.codefarm.planter.integrations

import com.codefarm.planter.model.Project
import org.springframework.stereotype.Service

@Service
class GithubClient {

    fun createRepository(project: Project) = project // Create a repository on GitHub
}