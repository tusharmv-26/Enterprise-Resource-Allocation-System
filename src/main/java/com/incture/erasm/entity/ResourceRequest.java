package com.incture.erasm.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.incture.erasm.enums.RequestStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resource_requests")
public class ResourceRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String requestCode;
	private String designation;
	private int requiredExperience;
	private int numberOfResources;
	
	@Enumerated(EnumType.STRING)
	private RequestStatus status;
	
	private LocalDate requestDate;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	@JsonBackReference
	private Project project;

	public ResourceRequest() {
		super();
	}

	public ResourceRequest(Long id, String requestCode, String designation, int requiredExperience,
			int numberOfResources, RequestStatus status, LocalDate requestDate, Project project) {
		super();
		this.id = id;
		this.requestCode = requestCode;
		this.designation = designation;
		this.requiredExperience = requiredExperience;
		this.numberOfResources = numberOfResources;
		this.status = status;
		this.requestDate = requestDate;
		this.project = project;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(String requestCode) {
		this.requestCode = requestCode;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getRequiredExperience() {
		return requiredExperience;
	}

	public void setRequiredExperience(int requiredExperience) {
		this.requiredExperience = requiredExperience;
	}

	public int getNumberOfResources() {
		return numberOfResources;
	}

	public void setNumberOfResources(int numberOfResources) {
		this.numberOfResources = numberOfResources;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}