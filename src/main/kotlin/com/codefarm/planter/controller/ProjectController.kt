package com.codefarm.planter.controller

import com.codefarm.planter.adapter.ProjectAdapter
import com.codefarm.planter.model.Project
import com.codefarm.planter.model.api.ProjectRequest
import com.codefarm.planter.service.ProjectService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projects")
class ProjectController(
    private val projectService: ProjectService,
    private val adapter: ProjectAdapter
) {

    @GetMapping
    fun getAll(): List<Project> = projectService.getAll()

    @PostMapping
    fun create(@RequestBody request: ProjectRequest) = request
        .let { adapter.toProject(request) }
        .let { projectService.create(it) }
        .let { adapter.toProjectResponse(it) }
}