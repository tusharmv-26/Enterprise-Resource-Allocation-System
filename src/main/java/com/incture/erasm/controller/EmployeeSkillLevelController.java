package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.incture.erasm.entity.EmployeeSkillLevel;
import com.incture.erasm.service.EmployeeSkillLevelService;

@RestController
@RequestMapping("/employee-skill-levels")
public class EmployeeSkillLevelController {
    @Autowired
    private EmployeeSkillLevelService employeeSkillLevelService;

    @PostMapping
    public EmployeeSkillLevel saveEmployeeSkillLevel(
            @RequestBody EmployeeSkillLevel employeeSkillLevel) {

        return employeeSkillLevelService.saveEmployeeSkillLevel(employeeSkillLevel);
    }

    @GetMapping
    public List<EmployeeSkillLevel> getAllEmployeeSkillLevels() {
        return employeeSkillLevelService.getAllEmployeeSkillLevels();
    }

    @GetMapping("/{id}")
    public EmployeeSkillLevel getEmployeeSkillLevelById(@PathVariable Long id) {
        return employeeSkillLevelService.getEmployeeSkillLevelById(id);
    }

    @PutMapping("/{id}")
    public EmployeeSkillLevel updateEmployeeSkillLevel(
            @PathVariable Long id,
            @RequestBody EmployeeSkillLevel employeeSkillLevel) {

        employeeSkillLevel.setId(id);

        return employeeSkillLevelService.updateEmployeeSkillLevel(employeeSkillLevel);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeSkillLevel(@PathVariable Long id) {

        employeeSkillLevelService.deleteEmployeeSkillLevel(id);

        return "Employee Skill Level deleted successfully";
    }
}