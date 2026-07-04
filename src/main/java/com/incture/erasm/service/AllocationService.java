package com.incture.erasm.service;

import java.time.LocalDate;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.dto.AllocationRequestDTO;
import com.incture.erasm.entity.Allocation;
import com.incture.erasm.repository.AllocationRepository;
import com.incture.erasm.repository.ProjectRepository;
import com.incture.erasm.repository.ResourceRequestRepository;
import com.incture.erasm.repository.EmployeeRepository;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.entity.Project;
import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.enums.RequestStatus;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.exception.AllocationException;
import com.incture.erasm.exception.ProjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AllocationService {
	private static final Logger logger = LoggerFactory.getLogger(AllocationService.class);
	@Autowired
	private AllocationRepository allocationRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ResourceRequestRepository resourceRequestRepository;
	@Autowired
	private AuditLogService auditLogService;
	
	private int getCurrentAllocationPercentage(Long employeeId) {
	    List<Allocation> allocations = allocationRepository.findByEmployeeId(employeeId);
	    int total = 0;
	    for (Allocation allocation : allocations) {
	        if (!"RELEASED".equalsIgnoreCase(allocation.getAllocationStatus())) {
	            if (allocation.getAllocationPercentage() != null) {
	                total += allocation.getAllocationPercentage();
	            }
	        }
	    }
	    return total;
	}
	
	public Allocation saveAllocation(AllocationRequestDTO dto) {
	    Employee employee = employeeRepository.findById(dto.getEmployeeId())
	            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
	    Project project = projectRepository.findById(dto.getProjectId())
	            .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + dto.getProjectId()));
	    ResourceRequest request = resourceRequestRepository.findById(dto.getResourceRequestId())
	            .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));

	    if (!employee.isAvailable()) {
	    	throw new AllocationException("Employee is not available for allocation");
	    }
	    if (request.getStatus() != RequestStatus.APPROVED) {
	    	throw new AllocationException("Only APPROVED requests can be allocated");
	    }
	    int currentAllocation = getCurrentAllocationPercentage(employee.getId());
	    if (currentAllocation + dto.getAllocationPercentage() > 100) {
	    	throw new AllocationException("Employee allocation cannot exceed 100%");
	    }
	    
	    Allocation allocation = new Allocation();
	    
	    allocation.setAllocationDate(dto.getAllocationDate());
	    allocation.setReleaseDate(dto.getReleaseDate());
	    allocation.setAllocationStatus(dto.getAllocationStatus());
	    allocation.setAllocationPercentage(dto.getAllocationPercentage());
	    
	    allocation.setEmployee(employee);
	    allocation.setProject(project);
	    allocation.setResourceRequest(request);

	    employee.setAvailable(false);

	    // Calculate Billable and Bench Hours
	    int billableHours = (employee.getTotalHours() * dto.getAllocationPercentage()) / 100;

	    employee.setBillableHours(billableHours);

	    employee.setBenchHours(employee.getTotalHours() - billableHours);

	    request.setStatus(RequestStatus.ALLOCATED);

	    employeeRepository.save(employee);
	    resourceRequestRepository.save(request);

	    Allocation savedAllocation = allocationRepository.save(allocation);
	    
	    logger.info("Employee allocated successfully. Allocation ID: {}", savedAllocation.getId());

	    auditLogService.saveAudit(
	            "ALLOCATE",
	            "Allocation",
	            savedAllocation.getId(),
	            "SYSTEM",
	            "Employee allocated successfully");
	    return savedAllocation;
	}
	
	public Allocation releaseEmployee(Long allocationId) {
		Allocation allocation = allocationRepository.findById(allocationId)
				.orElseThrow(() -> new AllocationException("Allocation not found"));
		
		if("RELEASED".equalsIgnoreCase(allocation.getAllocationStatus())) {
			throw new AllocationException("Employee already released");
		}
		
		Employee employee = allocation.getEmployee();
		
		employee.setAvailable(true);

		// Employee moved back to bench
		employee.setBillableHours(0);
		employee.setBenchHours(employee.getTotalHours());

		allocation.setAllocationStatus("RELEASED");
		allocation.setReleaseDate(LocalDate.now());

		employeeRepository.save(employee);

		Allocation releasedAllocation = allocationRepository.save(allocation);
		
		logger.info("Employee released successfully. Allocation ID: {}", releasedAllocation.getId());

		auditLogService.saveAudit(
		        "RELEASE",
		        "Allocation",
		        releasedAllocation.getId(),
		        "SYSTEM",
		        "Employee released successfully");
		return releasedAllocation;
	}

	public Allocation reallocateEmployee(Long allocationId, Long projectId, Long resourceRequestId) {
		Allocation allocation = allocationRepository.findById(allocationId)
				.orElseThrow(() -> new AllocationException("Allocation not found with ID: " + allocationId));
		
		Project project = projectRepository.findById(projectId)
				.orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));
		
		ResourceRequest request = resourceRequestRepository.findById(resourceRequestId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));
		
		allocation.setProject(project);
		allocation.setResourceRequest(request);
		
		request.setStatus(RequestStatus.ALLOCATED);
		
		resourceRequestRepository.save(request);

		Allocation reallocatedAllocation = allocationRepository.save(allocation);
		
		logger.info("Employee reallocated successfully. Allocation ID: {}", reallocatedAllocation.getId());

		auditLogService.saveAudit(
		        "REALLOCATE",
		        "Allocation",
		        reallocatedAllocation.getId(),
		        "SYSTEM",
		        "Employee reallocated successfully");
		return reallocatedAllocation;
	}
	
	public List<Allocation> getAllAllocations(){
		return allocationRepository.findAll();
	}
	
	public Allocation getAllocationById(Long id) {
		return allocationRepository.findById(id)
		        .orElseThrow(() -> new AllocationException("Allocation not found with ID: " + id));
	}
	
	public Allocation updateAllocation(Allocation allocation) {
	    Allocation updatedAllocation = allocationRepository.save(allocation);
	    logger.info("Allocation updated successfully. Allocation ID: {}",
	            updatedAllocation.getId());
	    auditLogService.saveAudit(
	            "UPDATE",
	            "Allocation",
	            updatedAllocation.getId(),
	            "SYSTEM",
	            "Allocation updated successfully");
	    return updatedAllocation;
	}
	
	public void deleteAllocation(Long id) {
	    logger.info("Deleting Allocation. Allocation ID: {}", id);
	    allocationRepository.deleteById(id);
	    auditLogService.saveAudit(
	            "DELETE",
	            "Allocation",
	            id,
	            "SYSTEM",
	            "Allocation deleted successfully");
	}
}