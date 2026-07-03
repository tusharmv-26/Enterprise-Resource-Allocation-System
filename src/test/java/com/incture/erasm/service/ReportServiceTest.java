package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.incture.erasm.dto.EmployeeSkillReportDTO;
import com.incture.erasm.dto.ProjectAllocationReportDTO;
import com.incture.erasm.dto.ResourceUtilizationReportDTO;
import com.incture.erasm.entity.Allocation;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.entity.Project;
import com.incture.erasm.entity.Skill;
import com.incture.erasm.entity.User;
import com.incture.erasm.repository.AllocationRepository;
import com.incture.erasm.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AllocationRepository allocationRepository;

    @InjectMocks
    private ReportService reportService;

    private Employee employee;
    private User user;
    private Skill skill;
    private Allocation allocation;
    private Project project;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("John Doe");

        skill = new Skill();
        skill.setSkillName("Java");
        skill.setSkillCategory("Backend");

        employee = new Employee();
        employee.setEmployeeCode("EMP001");
        employee.setDepartment("Engineering");
        employee.setDesignation("Software Engineer");
        employee.setExperience(3);
        employee.setUser(user);
        employee.setSkills(Set.of(skill));
        employee.setTotalHours(160);
        employee.setBillableHours(120);
        employee.setBenchHours(40);

        project = new Project();
        project.setProjectName("ERASM");

        allocation = new Allocation();
        allocation.setEmployee(employee);
        allocation.setProject(project);
        allocation.setAllocationPercentage(75);
        allocation.setAllocationDate(LocalDate.now());
        allocation.setReleaseDate(LocalDate.now().plusMonths(3));
        allocation.setAllocationStatus("ACTIVE");
    }

    @Test
    void testEmployeeSkillReportSuccess() {
        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee));

        List<EmployeeSkillReportDTO> report =
                reportService.getEmployeeSkillReport();

        assertEquals(1, report.size());

        EmployeeSkillReportDTO dto = report.get(0);

        assertEquals("EMP001", dto.getEmployeeCode());
        assertEquals("John Doe", dto.getEmployeeName());
        assertEquals("Java", dto.getSkillName());
        assertEquals("Backend", dto.getSkillCategory());

        verify(employeeRepository).findAll();
    }

    @Test
    void testEmployeeSkillReportEmpty() {
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());
        List<EmployeeSkillReportDTO> report =
                reportService.getEmployeeSkillReport();
        assertTrue(report.isEmpty());
    }

    @Test
    void testEmployeeWithoutSkills() {
        employee.setSkills(null);
        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee));
        List<EmployeeSkillReportDTO> report =
                reportService.getEmployeeSkillReport();
        assertTrue(report.isEmpty());
    }

    @Test
    void testProjectAllocationReportSuccess() {
        when(allocationRepository.findAll())
                .thenReturn(Arrays.asList(allocation));

        List<ProjectAllocationReportDTO> report =
                reportService.getProjectAllocationReport();

        assertEquals(1, report.size());

        ProjectAllocationReportDTO dto = report.get(0);

        assertEquals("ERASM", dto.getProjectName());
        assertEquals("EMP001", dto.getEmployeeCode());
        assertEquals("John Doe", dto.getEmployeeName());
        assertEquals(75, dto.getAllocationPercentage());

        verify(allocationRepository).findAll();
    }

    @Test
    void testProjectAllocationReportEmpty() {
        when(allocationRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<ProjectAllocationReportDTO> report =
                reportService.getProjectAllocationReport();

        assertTrue(report.isEmpty());
    }

    @Test
    void testResourceUtilizationReportSuccess() {
        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee));

        List<ResourceUtilizationReportDTO> report =
                reportService.getResourceUtilizationReport();

        assertEquals(1, report.size());

        ResourceUtilizationReportDTO dto = report.get(0);

        assertEquals(75.0,
                dto.getBillablePercentage(),0.01);

        assertEquals(25.0,
                dto.getBenchPercentage(),0.01);
    }

    @Test
    void testResourceUtilizationWithZeroHours() {
        employee.setTotalHours(0);
        employee.setBillableHours(0);
        employee.setBenchHours(0);

        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee));

        List<ResourceUtilizationReportDTO> report =
                reportService.getResourceUtilizationReport();

        assertNull(report.get(0).getBillablePercentage());
        assertNull(report.get(0).getBenchPercentage());
    }

    @Test
    void testResourceUtilizationEmptyEmployees() {
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());
        List<ResourceUtilizationReportDTO> report =
                reportService.getResourceUtilizationReport();
        assertTrue(report.isEmpty());
    }

    @Test
    void testRepositoryMethodsInvoked() {
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());
        when(allocationRepository.findAll())
                .thenReturn(Collections.emptyList());

        reportService.getEmployeeSkillReport();
        reportService.getProjectAllocationReport();
        reportService.getResourceUtilizationReport();

        verify(employeeRepository, times(2)).findAll();
        verify(allocationRepository, times(1)).findAll();
    }
}