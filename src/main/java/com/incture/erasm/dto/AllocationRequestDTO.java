package com.incture.erasm.dto;

import java.time.LocalDate;

public class AllocationRequestDTO {

	private LocalDate allocationDate;

	private LocalDate releaseDate;

	private String allocationStatus;

	private Integer allocationPercentage;

	private Long employeeId;

	private Long projectId;

	private Long resourceRequestId;

	public AllocationRequestDTO() {
		super();
	}

	public AllocationRequestDTO(LocalDate allocationDate,
			LocalDate releaseDate,
			String allocationStatus,
			Integer allocationPercentage,
			Long employeeId,
			Long projectId,
			Long resourceRequestId) {

		super();
		this.allocationDate = allocationDate;
		this.releaseDate = releaseDate;
		this.allocationStatus = allocationStatus;
		this.allocationPercentage = allocationPercentage;
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.resourceRequestId = resourceRequestId;
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

	public Integer getAllocationPercentage() {
		return allocationPercentage;
	}

	public void setAllocationPercentage(Integer allocationPercentage) {
		this.allocationPercentage = allocationPercentage;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getResourceRequestId() {
		return resourceRequestId;
	}

	public void setResourceRequestId(Long resourceRequestId) {
		this.resourceRequestId = resourceRequestId;
	}
}