package com.incture.erasm.dto;

public class EmployeeSkillReportDTO {

    private String employeeCode;
    private String employeeName;
    private String department;
    private String designation;
    private int experience;
    private String skillName;
    private String skillCategory;
    
	public EmployeeSkillReportDTO() {
		super();
	}

	public EmployeeSkillReportDTO(String employeeCode, String employeeName, String department, String designation,
			int experience, String skillName, String skillCategory) {
		super();
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.department = department;
		this.designation = designation;
		this.experience = experience;
		this.skillName = skillName;
		this.skillCategory = skillCategory;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public String getSkillCategory() {
		return skillCategory;
	}

	public void setSkillCategory(String skillCategory) {
		this.skillCategory = skillCategory;
	}
}