package com.incture.erasm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.incture.erasm.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
	long countByAvailableTrue();
	long countByAvailableFalse();
	List<Employee> findAll();
}