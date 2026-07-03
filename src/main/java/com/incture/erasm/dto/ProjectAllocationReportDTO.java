package com.incture.erasm.dto;

import java.time.LocalDate;

public class ProjectAllocationReportDTO {
	private String projectName;
    private String employeeCode;
    private String employeeName;
    private Integer allocationPercentage;
    private LocalDate allocationDate;
    private LocalDate releaseDate;
    private String allocationStatus;
    
	public ProjectAllocationReportDTO() {
		super();
	}

	public ProjectAllocationReportDTO(String projectName, String employeeCode, String employeeName,
			Integer allocationPercentage, LocalDate allocationDate, LocalDate releaseDate, String allocationStatus) {
		super();
		this.projectName = projectName;
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.allocationPercentage = allocationPercentage;
		this.allocationDate = allocationDate;
		this.releaseDate = releaseDate;
		this.allocationStatus = allocationStatus;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public Integer getAllocationPercentage() {
		return allocationPercentage;
	}

	public void setAllocationPercentage(Integer allocationPercentage) {
		this.allocationPercentage = allocationPercentage;
	}

	public LocalDate getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(LocalDate allocationDate) {
		this.allocationDate = allocationDate;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAllocationStatus() {
		return allocationStatus;
	}

	public void setAllocationStatus(String allocationStatus) {
		this.allocationStatus = allocationStatus;
	}
}