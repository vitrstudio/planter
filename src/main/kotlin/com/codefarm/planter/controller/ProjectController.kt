package com.codefarm.planter.controller

import com.codefarm.planter.model.Project
import com.codefarm.planter.service.ProjectService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/projects")
class ProjectController(private val projectService: ProjectService) {

    @GetMapping
    fun getAll(): List<Project> = projectService.getAll()
}