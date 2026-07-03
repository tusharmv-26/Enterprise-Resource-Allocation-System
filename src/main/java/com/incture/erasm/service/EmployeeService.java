package com.incture.erasm.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.entity.Employee;
import com.incture.erasm.entity.Project;
import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.entity.Skill;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.EmployeeRepository;
import com.incture.erasm.repository.ResourceRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmployeeService {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ResourceRequestRepository resourceRequestRepository;
	
	@Autowired
	private AuditLogService auditLogService;
	
	public Employee saveEmployee(Employee employee){
	    Employee savedEmployee = employeeRepository.save(employee);
	    logger.info("Employee created successfully with ID: {}", savedEmployee.getId());
	    auditLogService.saveAudit("CREATE", "Employee", savedEmployee.getId(), "SYSTEM", "Employee created successfully");
	    return savedEmployee;
	}
	
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}
	
	public Employee getEmployeeById(Long id){
	    return employeeRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
	}
	
	public Employee updateEmployee(Employee employee){
	    Employee updatedEmployee = employeeRepository.save(employee);
	    logger.info("Employee updated successfully with ID: {}", updatedEmployee.getId());
	    auditLogService.saveAudit("UPDATE", "Employee", updatedEmployee.getId(), "SYSTEM", "Employee updated successfully");
	    return updatedEmployee;
	}
	
	public void deleteEmployee(Long id){
		logger.info("Deleting employee with ID: {}", id);
	    employeeRepository.deleteById(id);
	    auditLogService.saveAudit("DELETE", "Employee", id, "SYSTEM", "Employee deleted successfully");
	}
	
	public List<Employee> recommendEmployees(Long requestId){
		ResourceRequest request = resourceRequestRepository.findById(requestId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));
		
		Project project = request.getProject();
		Set<Skill> requiredSkills = project.getRequiredSkills();
		int requiredExperience = request.getRequiredExperience();
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream()
				.filter(Employee::isAvailable)
				.filter(emp -> emp.getExperience() >= requiredExperience)
				.filter(emp -> emp.getSkills().containsAll(requiredSkills))
				.toList();
	}
}