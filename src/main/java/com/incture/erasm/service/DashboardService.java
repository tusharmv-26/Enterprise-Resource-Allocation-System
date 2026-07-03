package com.incture.erasm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.dto.DashboardDTO;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.enums.RequestStatus;
import com.incture.erasm.repository.EmployeeRepository;
import com.incture.erasm.repository.ProjectRepository;
import com.incture.erasm.repository.ResourceRequestRepository;
import com.incture.erasm.repository.SkillRepository;

@Service
public class DashboardService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private ResourceRequestRepository resourceRequestRepository;

    public DashboardDTO getDashboard() {
        DashboardDTO dashboard = new DashboardDTO();

        dashboard.setTotalEmployees(employeeRepository.count());
        dashboard.setAvailableEmployees(employeeRepository.countByAvailableTrue());
        dashboard.setAllocatedEmployees(employeeRepository.countByAvailableFalse());
        dashboard.setTotalProjects(projectRepository.count());
        dashboard.setActiveProjects(projectRepository.countByStatus("ACTIVE"));
        dashboard.setTotalSkills(skillRepository.count());
        dashboard.setPendingRequests(resourceRequestRepository.countByStatus(RequestStatus.DRAFT));
        dashboard.setApprovedRequests(resourceRequestRepository.countByStatus(RequestStatus.APPROVED));
        dashboard.setCompletedRequests(resourceRequestRepository.countByStatus(RequestStatus.COMPLETED));

        List<Employee> employees = employeeRepository.findAll();

        double totalHours = 0;
        double billableHours = 0;
        double benchHours = 0;

        for (Employee employee : employees) {
            if (employee.getTotalHours() != null)
                totalHours += employee.getTotalHours();
            if (employee.getBillableHours() != null)
                billableHours += employee.getBillableHours();
            if (employee.getBenchHours() != null)
                benchHours += employee.getBenchHours();
        }

        if (totalHours > 0) {
            dashboard.setBillablePercentage(
                    (billableHours / totalHours) * 100);
            dashboard.setBenchPercentage(
                    (benchHours / totalHours) * 100);
        }
        return dashboard;
    }
}