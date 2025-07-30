package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.ProjectAdapter
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.api.ProjectResponse
import studio.vitr.planter.service.ProjectService
import studio.vitr.planter.service.UserService

@RestController
@RequestMapping("/users/{userId}/projects")
class UserProjectController(
    private val userService: UserService,
    private val projectService: ProjectService,
    private val projectAdapter: ProjectAdapter,
) {

    @GetMapping
    fun getProjects(@PathVariable userId: Long)= projectService.getByUserId(userId)
        .map { projectAdapter.toProjectResponse(it) }

    @PostMapping
    fun createProject(
        @PathVariable userId: Long,
        @RequestBody request: ProjectRequest,
    ): ProjectResponse {
        val user = userService.get(userId) ?: throw NotFound(USER, userId.toString())
        return projectService.create(user, request)
            .let { projectAdapter.toProjectResponse(it) }
    }

    @DeleteMapping("/{projectId}")
    fun deleteProject(
        @PathVariable userId: Long,
        @PathVariable projectId: Long
    ) = projectService.delete(projectId)
}