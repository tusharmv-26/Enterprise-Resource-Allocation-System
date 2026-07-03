package com.incture.erasm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.dto.EmployeeSkillReportDTO;
import com.incture.erasm.dto.ProjectAllocationReportDTO;
import com.incture.erasm.dto.ResourceUtilizationReportDTO;
import com.incture.erasm.entity.Allocation;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.entity.Skill;
import com.incture.erasm.repository.AllocationRepository;
import com.incture.erasm.repository.EmployeeRepository;

@Service
public class ReportService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    public List<EmployeeSkillReportDTO> getEmployeeSkillReport() {
        List<EmployeeSkillReportDTO> report = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            if (employee.getSkills() != null) {
                for (Skill skill : employee.getSkills()) {
                    EmployeeSkillReportDTO dto = new EmployeeSkillReportDTO();

                    dto.setEmployeeCode(employee.getEmployeeCode());
                    dto.setEmployeeName(employee.getUser().getName());
                    dto.setDepartment(employee.getDepartment());
                    dto.setDesignation(employee.getDesignation());
                    dto.setExperience(employee.getExperience());
                    dto.setSkillName(skill.getSkillName());
                    dto.setSkillCategory(skill.getSkillCategory());

                    report.add(dto);
                }
            }
        }
        return report;
    }
    
    public List<ProjectAllocationReportDTO> getProjectAllocationReport() {
        List<ProjectAllocationReportDTO> report = new ArrayList<>();
        List<Allocation> allocations = allocationRepository.findAll();
        for (Allocation allocation : allocations) {
            ProjectAllocationReportDTO dto = new ProjectAllocationReportDTO();
            dto.setProjectName(allocation.getProject().getProjectName());
            dto.setEmployeeCode(
                    allocation.getEmployee().getEmployeeCode());
            dto.setEmployeeName(
                    allocation.getEmployee().getUser().getName());
            dto.setAllocationPercentage(
                    allocation.getAllocationPercentage());
            dto.setAllocationDate(
                    allocation.getAllocationDate());
            dto.setReleaseDate(
                    allocation.getReleaseDate());
            dto.setAllocationStatus(
                    allocation.getAllocationStatus());
            report.add(dto);
        }
        return report;
    }

    public List<ResourceUtilizationReportDTO> getResourceUtilizationReport() {
        List<ResourceUtilizationReportDTO> report = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            ResourceUtilizationReportDTO dto =
                    new ResourceUtilizationReportDTO();
            dto.setEmployeeCode(employee.getEmployeeCode());
            dto.setEmployeeName(
                    employee.getUser().getName());
            dto.setTotalHours(
                    employee.getTotalHours());
            dto.setBillableHours(
                    employee.getBillableHours());
            dto.setBenchHours(
                    employee.getBenchHours());
            if (employee.getTotalHours() != null
                    && employee.getTotalHours() > 0) {
                double billable =
                        ((double) employee.getBillableHours()
                                / employee.getTotalHours()) * 100;
                double bench =
                        ((double) employee.getBenchHours()
                                / employee.getTotalHours()) * 100;
                dto.setBillablePercentage(billable);
                dto.setBenchPercentage(bench);
            }
            report.add(dto);
        }
        return report;
    }
}