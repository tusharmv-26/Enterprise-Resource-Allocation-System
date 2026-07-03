package com.incture.erasm.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String projectName;

	private String clientName;

	private LocalDate startDate;

	private LocalDate endDate;

	private String status;
	
	private Double budget;

	@ManyToMany
	@JoinTable(
		name = "project_skills",
		joinColumns = @JoinColumn(name = "project_id"),
		inverseJoinColumns = @JoinColumn(name = "skill_id")
	)
	private Set<Skill> requiredSkills;

	@OneToMany(mappedBy = "project")
	@JsonManagedReference
	private List<ResourceRequest> resourceRequests;

	public Project() {
		super();
	}

	public Project(Long id, String projectName, String clientName,
			LocalDate startDate, LocalDate endDate,
			String status, Double budget, Set<Skill> requiredSkills,
			List<ResourceRequest> resourceRequests) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.clientName = clientName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.budget = budget;
		this.requiredSkills = requiredSkills;
		this.resourceRequests = resourceRequests;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Set<Skill> getRequiredSkills() {
		return requiredSkills;
	}

	public void setRequiredSkills(Set<Skill> requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public List<ResourceRequest> getResourceRequests() {
		return resourceRequests;
	}

	public void setResourceRequests(List<ResourceRequest> resourceRequests) {
		this.resourceRequests = resourceRequests;
	}
}