package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.*
import studio.vitr.planter.adapter.ProjectAdapter
import studio.vitr.planter.model.api.ProjectRequest
import studio.vitr.planter.service.ProjectService
import java.util.*

@RestController
@RequestMapping("/users/{userId}/projects")
class UserProjectController(
    private val service: ProjectService,
    private val adapter: ProjectAdapter,
) {

    @GetMapping
    fun getProjects(@PathVariable userId: UUID)= service.getByUserId(userId)
        .map { adapter.toProjectResponse(it) }

    @PostMapping
    fun createProject(
        @PathVariable userId: UUID,
        @RequestBody request: ProjectRequest,
    ) = service.create(userId, request)

    @DeleteMapping("/{projectId}")
    fun deleteProject(
        @PathVariable userId: UUID,
        @PathVariable projectId: UUID
    ) = service.delete(userId, projectId)
}