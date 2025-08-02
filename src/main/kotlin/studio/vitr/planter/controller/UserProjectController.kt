package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.ProjectAdapter
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.errors.NotFound
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.model.api.ProjectResponse
import studio.vitr.planter.service.ProjectService
import studio.vitr.planter.service.UserService
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
        return projectService.create(user, request)
            .let { projectAdapter.toProjectResponse(it) }
    }

    @DeleteMapping("/{projectId}")
    fun deleteProject(
        @PathVariable userId: UUID,
        @PathVariable projectId: UUID
    ) = projectService.delete(projectId)
}