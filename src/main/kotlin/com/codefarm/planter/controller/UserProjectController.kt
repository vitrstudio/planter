package com.codefarm.planter.controller

import com.codefarm.planter.adapter.ProjectAdapter
import com.codefarm.planter.constants.Properties.USER
import com.codefarm.planter.errors.NotFound
import com.codefarm.planter.model.api.ProjectRequest
import com.codefarm.planter.model.api.ProjectResponse
import com.codefarm.planter.service.ProjectService
import com.codefarm.planter.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/users/{userId}/projects")
class UserProjectController(
    private val userService: UserService,
    private val projectService: ProjectService,
    private val projectAdapter: ProjectAdapter,
) {

    @GetMapping
    fun getProjects(@PathVariable userId: UUID)= projectService.getByUserId(userId)
        .map { projectAdapter.toProjectResponse(it) }

    @PostMapping
    fun createProject(
        @PathVariable userId: UUID,
        @RequestBody request: ProjectRequest,
    ): ProjectResponse {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        return projectAdapter.toProject(request, user)
            .let { projectService.create(it) }
            .let { projectAdapter.toProjectResponse(it) }
    }

    @DeleteMapping("/{projectId}")
    fun deleteProject(
        @PathVariable userId: UUID,
        @PathVariable projectId: UUID
    ) = projectService.delete(projectId)
}