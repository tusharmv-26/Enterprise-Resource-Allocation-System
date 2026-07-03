package com.incture.erasm.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "allocations")
public class Allocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate allocationDate;

	private LocalDate releaseDate;

	private String allocationStatus;

	private Integer allocationPercentage;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@ManyToOne
	@JoinColumn(name = "resource_request_id")
	private ResourceRequest resourceRequest;

	public Allocation() {
		super();
	}

	public Allocation(Long id, LocalDate allocationDate, LocalDate releaseDate,
			String allocationStatus, Integer allocationPercentage,
			Employee employee, Project project,
			ResourceRequest resourceRequest) {

		super();

		this.id = id;
		this.allocationDate = allocationDate;
		this.releaseDate = releaseDate;
		this.allocationStatus = allocationStatus;
		this.allocationPercentage = allocationPercentage;
		this.employee = employee;
		this.project = project;
		this.resourceRequest = resourceRequest;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ResourceRequest getResourceRequest() {
		return resourceRequest;
	}

	public void setResourceRequest(ResourceRequest resourceRequest) {
		this.resourceRequest = resourceRequest;
	}
}