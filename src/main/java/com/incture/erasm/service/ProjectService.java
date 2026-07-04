package com.incture.erasm.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.entity.Project;
import com.incture.erasm.exception.ProjectNotFoundException;
import com.incture.erasm.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProjectService {
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuditLogService auditLogService;

    public Project saveProject(Project project) {
        Project savedProject = projectRepository.save(project);
        logger.info("Project created successfully with ID: {}", savedProject.getId());
        auditLogService.saveAudit("CREATE", "Project", savedProject.getId(), "SYSTEM", "Project created successfully");
        return savedProject;
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + id));
    }

    public Project updateProject(Project project) {
        Project updatedProject = projectRepository.save(project);
        logger.info("Project updated successfully with ID: {}", updatedProject.getId());
        auditLogService.saveAudit("UPDATE", "Project", updatedProject.getId(), "SYSTEM", "Project updated successfully");
        return updatedProject;
    }

    public Project closeProject(Long id) {
    	Project project = projectRepository.findById(id)
    	        .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + id));
        project.setStatus("CLOSED");
        Project closedProject = projectRepository.save(project);
        auditLogService.saveAudit("CLOSE", "Project", closedProject.getId(), "SYSTEM", "Project closed successfully");
        return closedProject;
    }

    public void deleteProject(Long id) {
    	logger.info("Deleting project with ID: {}", id);
        projectRepository.deleteById(id);
        auditLogService.saveAudit( "DELETE", "Project", id, "SYSTEM", "Project deleted successfully");
    }
}