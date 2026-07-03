package com.incture.erasm.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String employeeCode;

	private String designation;

	private int experience;

	private String department;

	private boolean available;
	
	private Integer totalHours;

	private Integer billableHours;

	private Integer benchHours;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany
	@JoinTable(
		name = "employee_skills",
		joinColumns = @JoinColumn(name = "employee_id"),
		inverseJoinColumns = @JoinColumn(name = "skill_id")
	)
	private Set<Skill> skills;
	
	@OneToMany(mappedBy = "employee")
	@JsonManagedReference
	private List<Certification> certifications;

	public Employee() {
		super();
	}

	private Employee(Long id, String employeeCode, String designation, int experience, String department,
			boolean available, Integer totalHours, Integer billableHours, Integer benchHours, User user,
			Set<Skill> skills, List<Certification> certifications) {
		super();
		this.id = id;
		this.employeeCode = employeeCode;
		this.designation = designation;
		this.experience = experience;
		this.department = department;
		this.available = available;
		this.totalHours = totalHours;
		this.billableHours = billableHours;
		this.benchHours = benchHours;
		this.user = user;
		this.skills = skills;
		this.certifications = certifications;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Integer getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Integer totalHours) {
		this.totalHours = totalHours;
	}

	public Integer getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(Integer billableHours) {
		this.billableHours = billableHours;
	}

	public Integer getBenchHours() {
		return benchHours;
	}

	public void setBenchHours(Integer benchHours) {
		this.benchHours = benchHours;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Skill> getSkills() {
		return skills;
	}

	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}
}