package com.incture.erasm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.entity.Employee;
import com.incture.erasm.entity.EmployeeSkillLevel;
import com.incture.erasm.entity.Skill;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.EmployeeRepository;
import com.incture.erasm.repository.EmployeeSkillLevelRepository;
import com.incture.erasm.repository.SkillRepository;

@Service
public class EmployeeSkillLevelService {
    @Autowired
    private EmployeeSkillLevelRepository employeeSkillLevelRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SkillRepository skillRepository;

    public EmployeeSkillLevel saveEmployeeSkillLevel(EmployeeSkillLevel employeeSkillLevel) {

        Long employeeId = employeeSkillLevel.getEmployee().getId();
        Long skillId = employeeSkillLevel.getSkill().getId();

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

        employeeSkillLevel.setEmployee(employee);
        employeeSkillLevel.setSkill(skill);

        return employeeSkillLevelRepository.save(employeeSkillLevel);
    }

    public List<EmployeeSkillLevel> getAllEmployeeSkillLevels() {
        return employeeSkillLevelRepository.findAll();
    }

    public EmployeeSkillLevel getEmployeeSkillLevelById(Long id) {
        return employeeSkillLevelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Skill Level not found"));
    }

    public EmployeeSkillLevel updateEmployeeSkillLevel(EmployeeSkillLevel employeeSkillLevel) {
        return employeeSkillLevelRepository.save(employeeSkillLevel);
    }

    public void deleteEmployeeSkillLevel(Long id) {
        employeeSkillLevelRepository.deleteById(id);
    }
}