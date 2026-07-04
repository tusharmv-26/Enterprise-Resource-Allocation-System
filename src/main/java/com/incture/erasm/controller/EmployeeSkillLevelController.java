package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.incture.erasm.entity.EmployeeSkillLevel;
import com.incture.erasm.service.EmployeeSkillLevelService;

@RestController
@RequestMapping("/employee-skill-levels")
public class EmployeeSkillLevelController {
    @Autowired
    private EmployeeSkillLevelService employeeSkillLevelService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public EmployeeSkillLevel saveEmployeeSkillLevel(@RequestBody EmployeeSkillLevel employeeSkillLevel) {
        return employeeSkillLevelService.saveEmployeeSkillLevel(employeeSkillLevel);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<EmployeeSkillLevel> getAllEmployeeSkillLevels() {
        return employeeSkillLevelService.getAllEmployeeSkillLevels();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public EmployeeSkillLevel getEmployeeSkillLevelById(@PathVariable Long id) {
        return employeeSkillLevelService.getEmployeeSkillLevelById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public EmployeeSkillLevel updateEmployeeSkillLevel(@PathVariable Long id, @RequestBody EmployeeSkillLevel employeeSkillLevel) {
        employeeSkillLevel.setId(id);
        return employeeSkillLevelService.updateEmployeeSkillLevel(employeeSkillLevel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteEmployeeSkillLevel(@PathVariable Long id) {
        employeeSkillLevelService.deleteEmployeeSkillLevel(id);
        return "Employee Skill Level deleted successfully";
    }
}