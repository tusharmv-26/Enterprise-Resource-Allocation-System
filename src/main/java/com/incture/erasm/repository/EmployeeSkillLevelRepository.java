package com.incture.erasm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.erasm.entity.EmployeeSkillLevel;

@Repository
public interface EmployeeSkillLevelRepository extends JpaRepository<EmployeeSkillLevel, Long> {

}