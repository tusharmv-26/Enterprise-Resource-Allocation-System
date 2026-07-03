package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incture.erasm.dto.DashboardDTO;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.enums.RequestStatus;
import com.incture.erasm.repository.EmployeeRepository;
import com.incture.erasm.repository.ProjectRepository;
import com.incture.erasm.repository.ResourceRequestRepository;
import com.incture.erasm.repository.SkillRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private ResourceRequestRepository resourceRequestRepository;

    @InjectMocks
    private DashboardService dashboardService;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        employee1 = new Employee();
        employee1.setTotalHours(160);
        employee1.setBillableHours(120);
        employee1.setBenchHours(40);

        employee2 = new Employee();
        employee2.setTotalHours(160);
        employee2.setBillableHours(80);
        employee2.setBenchHours(80);
    }

    @Test
    void testGetDashboardSuccess() {
        when(employeeRepository.count()).thenReturn(10L);
        when(employeeRepository.countByAvailableTrue()).thenReturn(4L);
        when(employeeRepository.countByAvailableFalse()).thenReturn(6L);

        when(projectRepository.count()).thenReturn(5L);
        when(projectRepository.countByStatus("ACTIVE")).thenReturn(3L);

        when(skillRepository.count()).thenReturn(15L);

        when(resourceRequestRepository.countByStatus(RequestStatus.DRAFT)).thenReturn(2L);
        when(resourceRequestRepository.countByStatus(RequestStatus.APPROVED)).thenReturn(5L);
        when(resourceRequestRepository.countByStatus(RequestStatus.COMPLETED)).thenReturn(1L);

        when(employeeRepository.findAll())
                .thenReturn(Arrays.asList(employee1, employee2));

        DashboardDTO dto = dashboardService.getDashboard();

        assertNotNull(dto);

        assertEquals(10L, dto.getTotalEmployees());
        assertEquals(4L, dto.getAvailableEmployees());
        assertEquals(6L, dto.getAllocatedEmployees());

        assertEquals(5L, dto.getTotalProjects());
        assertEquals(3L, dto.getActiveProjects());

        assertEquals(15L, dto.getTotalSkills());

        assertEquals(2L, dto.getPendingRequests());
        assertEquals(5L, dto.getApprovedRequests());
        assertEquals(1L, dto.getCompletedRequests());

        assertEquals(62.5, dto.getBillablePercentage(), 0.01);
        assertEquals(37.5, dto.getBenchPercentage(), 0.01);

        verify(employeeRepository).findAll();
    }

    @Test
    void testDashboardWithNoEmployees() {
        when(employeeRepository.count()).thenReturn(0L);
        when(employeeRepository.countByAvailableTrue()).thenReturn(0L);
        when(employeeRepository.countByAvailableFalse()).thenReturn(0L);

        when(projectRepository.count()).thenReturn(0L);
        when(projectRepository.countByStatus("ACTIVE")).thenReturn(0L);

        when(skillRepository.count()).thenReturn(0L);

        when(resourceRequestRepository.countByStatus(RequestStatus.DRAFT)).thenReturn(0L);
        when(resourceRequestRepository.countByStatus(RequestStatus.APPROVED)).thenReturn(0L);
        when(resourceRequestRepository.countByStatus(RequestStatus.COMPLETED)).thenReturn(0L);

        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());

        DashboardDTO dto = dashboardService.getDashboard();

        assertNotNull(dto);
        assertEquals(0L, dto.getTotalEmployees());
        assertEquals(0.0, dto.getBillablePercentage());
        assertEquals(0.0, dto.getBenchPercentage());
    }

    @Test
    void testDashboardWithZeroTotalHours() {
        employee1.setTotalHours(0);
        employee1.setBillableHours(0);
        employee1.setBenchHours(0);

        when(employeeRepository.findAll())
                .thenReturn(Collections.singletonList(employee1));

        DashboardDTO dto = dashboardService.getDashboard();

        assertEquals(0.0, dto.getBillablePercentage());
        assertEquals(0.0, dto.getBenchPercentage());
    }

    @Test
    void testDashboardWithNullHours() {
        employee1.setTotalHours(null);
        employee1.setBillableHours(null);
        employee1.setBenchHours(null);

        when(employeeRepository.findAll())
                .thenReturn(Collections.singletonList(employee1));

        DashboardDTO dto = dashboardService.getDashboard();

        assertNotNull(dto);
        assertEquals(0.0, dto.getBillablePercentage());
        assertEquals(0.0, dto.getBenchPercentage());
    }

    @Test
    void testBillableCalculationOnly() {
        employee1.setTotalHours(200);
        employee1.setBillableHours(150);
        employee1.setBenchHours(50);

        when(employeeRepository.findAll())
                .thenReturn(Collections.singletonList(employee1));

        DashboardDTO dto = dashboardService.getDashboard();

        assertEquals(75.0, dto.getBillablePercentage(), 0.01);
    }

    @Test
    void testBenchCalculationOnly() {

        employee1.setTotalHours(200);
        employee1.setBillableHours(100);
        employee1.setBenchHours(100);

        when(employeeRepository.findAll())
                .thenReturn(Collections.singletonList(employee1));

        DashboardDTO dto = dashboardService.getDashboard();

        assertEquals(50.0, dto.getBenchPercentage(), 0.01);
    }

    @Test
    void testRepositoryMethodsInvoked() {
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());

        dashboardService.getDashboard();

        verify(employeeRepository, times(1)).count();
        verify(employeeRepository, times(1)).countByAvailableTrue();
        verify(employeeRepository, times(1)).countByAvailableFalse();

        verify(projectRepository, times(1)).count();
        verify(projectRepository, times(1)).countByStatus("ACTIVE");

        verify(skillRepository, times(1)).count();

        verify(resourceRequestRepository, times(1))
                .countByStatus(RequestStatus.DRAFT);

        verify(resourceRequestRepository, times(1))
                .countByStatus(RequestStatus.APPROVED);

        verify(resourceRequestRepository, times(1))
                .countByStatus(RequestStatus.COMPLETED);

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testDashboardObjectNotNull() {
        when(employeeRepository.findAll())
                .thenReturn(Collections.emptyList());

        DashboardDTO dto = dashboardService.getDashboard();

        assertNotNull(dto);
    }
}