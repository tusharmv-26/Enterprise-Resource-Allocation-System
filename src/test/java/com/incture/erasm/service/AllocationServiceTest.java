package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incture.erasm.dto.AllocationRequestDTO;
import com.incture.erasm.entity.Allocation;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.entity.Project;
import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.enums.RequestStatus;
import com.incture.erasm.repository.AllocationRepository;
import com.incture.erasm.repository.EmployeeRepository;
import com.incture.erasm.repository.ProjectRepository;
import com.incture.erasm.repository.ResourceRequestRepository;

@ExtendWith(MockitoExtension.class)
class AllocationServiceTest {

    @Mock
    private AllocationRepository allocationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ResourceRequestRepository resourceRequestRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private AllocationService allocationService;
    
    @Test
    void testSaveAllocation() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setAvailable(true);
        employee.setTotalHours(160);

        Project project = new Project();
        project.setId(1L);

        ResourceRequest request = new ResourceRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.APPROVED);

        Allocation allocation = new Allocation();
        allocation.setId(1L);

        AllocationRequestDTO dto = new AllocationRequestDTO();

        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setResourceRequestId(1L);
        dto.setAllocationPercentage(50);
        dto.setAllocationStatus("ALLOCATED");
        dto.setAllocationDate(LocalDate.now());

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(allocationRepository.findByEmployeeId(1L))
                .thenReturn(Collections.emptyList());

        when(allocationRepository.save(any(Allocation.class)))
                .thenReturn(allocation);

        Allocation result = allocationService.saveAllocation(dto);

        assertNotNull(result);

        verify(allocationRepository).save(any(Allocation.class));

        verify(employeeRepository).save(employee);

        verify(resourceRequestRepository).save(request);
    }
    
    @Test
    void testSaveAllocationEmployeeUnavailable() {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setAvailable(false);

        AllocationRequestDTO dto = new AllocationRequestDTO();
        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setResourceRequestId(1L);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        Project project = new Project();
        project.setId(1L);

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        ResourceRequest request = new ResourceRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.APPROVED);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> allocationService.saveAllocation(dto));

        assertEquals(
                "Employee is not available for allocation",
                exception.getMessage());
    }
    
    @Test
    void testAllocationExceeds100Percent() {

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setAvailable(true);

        Project project = new Project();
        project.setId(1L);

        ResourceRequest request = new ResourceRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.APPROVED);

        Allocation existing = new Allocation();
        existing.setAllocationPercentage(80);
        existing.setAllocationStatus("ALLOCATED");

        AllocationRequestDTO dto = new AllocationRequestDTO();

        dto.setEmployeeId(1L);
        dto.setProjectId(1L);
        dto.setResourceRequestId(1L);
        dto.setAllocationPercentage(30);

        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(allocationRepository.findByEmployeeId(1L))
                .thenReturn(List.of(existing));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> allocationService.saveAllocation(dto));

        assertEquals(
                "Employee allocation cannot exceed 100%",
                exception.getMessage());
    }
    
    @Test
    void testReleaseEmployee() {

        Employee employee = new Employee();
        employee.setAvailable(false);
        employee.setTotalHours(160);

        Allocation allocation = new Allocation();
        allocation.setId(1L);
        allocation.setEmployee(employee);
        allocation.setAllocationStatus("ALLOCATED");

        when(allocationRepository.findById(1L))
                .thenReturn(Optional.of(allocation));

        when(allocationRepository.save(any(Allocation.class)))
                .thenReturn(allocation);

        Allocation result = allocationService.releaseEmployee(1L);

        assertEquals("RELEASED", result.getAllocationStatus());

        verify(employeeRepository).save(employee);

        verify(allocationRepository).save(allocation);
    }

}