package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.erasm.dto.EmployeeSkillReportDTO;
import com.incture.erasm.dto.ProjectAllocationReportDTO;
import com.incture.erasm.dto.ResourceUtilizationReportDTO;
import com.incture.erasm.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/employee-skills")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','RESOURCE_MANAGER')")
    public List<EmployeeSkillReportDTO> getEmployeeSkillReport() {
        return reportService.getEmployeeSkillReport();
    }

    @GetMapping("/project-allocations")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','RESOURCE_MANAGER')")
    public List<ProjectAllocationReportDTO> getProjectAllocationReport() {
        return reportService.getProjectAllocationReport();
    }

    @GetMapping("/resource-utilization")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','RESOURCE_MANAGER')")
    public List<ResourceUtilizationReportDTO> getResourceUtilizationReport() {
        return reportService.getResourceUtilizationReport();
    }
}