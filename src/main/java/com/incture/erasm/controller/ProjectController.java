package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.erasm.entity.Project;
import com.incture.erasm.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@PostMapping
	public Project saveProject(@RequestBody Project project) {
		return projectService.saveProject(project);
	}
	
	@GetMapping
	public List<Project> getAllProjects(){
		return projectService.getAllProjects();
	}
	
	@GetMapping("/{id}")
	public Project getProjectById(@PathVariable Long id) {
		return projectService.getProjectById(id);
	}
	
	@PutMapping("/{id}")
	public Project updateProject(@PathVariable Long id, @RequestBody Project project) {
		project.setId(id);
		return projectService.updateProject(project);
	}
	
	@PutMapping("/close/{id}")
	public Project closeProject(@PathVariable Long id) {
	    return projectService.closeProject(id);
	}
	
	@DeleteMapping("/{id}")
	public String deleteProject(@PathVariable Long id) {
		projectService.deleteProject(id);
		return "Project deleted successfully";
	}
}