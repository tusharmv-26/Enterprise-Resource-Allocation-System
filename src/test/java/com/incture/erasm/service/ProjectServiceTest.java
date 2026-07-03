package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incture.erasm.entity.Project;
import com.incture.erasm.repository.ProjectRepository;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private ProjectService projectService;

    @Test
    void testGetProjectById() {
        Project project = new Project();
        project.setId(1L);
        project.setProjectName("ERASM");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        Project result = projectService.getProjectById(1L);

        assertNotNull(result);
        assertEquals("ERASM", result.getProjectName());

        verify(projectRepository).findById(1L);
    }
    
    @Test
    void testSaveProject() {
        Project project = new Project();
        project.setId(1L);
        project.setProjectName("ERASM");

        when(projectRepository.save(project))
                .thenReturn(project);

        Project savedProject = projectService.saveProject(project);

        assertNotNull(savedProject);
        assertEquals("ERASM", savedProject.getProjectName());

        verify(projectRepository).save(project);

        verify(auditLogService)
                .saveAudit(
                        "CREATE",
                        "Project",
                        1L,
                        "SYSTEM",
                        "Project created successfully");
    }
    
    @Test
    void testCloseProject() {
        Project project = new Project();

        project.setId(1L);
        project.setStatus("ACTIVE");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(projectRepository.save(project))
                .thenReturn(project);

        Project closedProject = projectService.closeProject(1L);

        assertEquals("CLOSED", closedProject.getStatus());

        verify(projectRepository).findById(1L);

        verify(projectRepository).save(project);

        verify(auditLogService)
                .saveAudit(
                        "CLOSE",
                        "Project",
                        1L,
                        "SYSTEM",
                        "Project closed successfully");
    }
}