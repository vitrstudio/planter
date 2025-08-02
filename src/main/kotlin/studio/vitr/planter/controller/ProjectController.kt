package studio.vitr.planter.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import studio.vitr.planter.adapter.ProjectAdapter
import studio.vitr.planter.constants.Properties.USER
import studio.vitr.planter.constants.Properties.USER_ID
import studio.vitr.planter.errors.MissingExpectedParameter
import studio.vitr.planter.service.ProjectService
import studio.vitr.planter.service.UserService

@RestController
@RequestMapping("/projects")
class ProjectController(
    private val projectService: ProjectService,
    private val userService: UserService,
    private val projectAdapter: ProjectAdapter,
) {

    @GetMapping
    fun getAll() = projectService
        .getAll()
        .map { Pair(it, userService.get(it.user.id ?: throw MissingExpectedParameter(USER_ID))) }
        .map { Pair(it.first, it.second ?: throw MissingExpectedParameter(USER)) }
        .map { projectAdapter.toProjectWithUserResponse(it.first, it.second) }
}