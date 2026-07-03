package com.incture.erasm.dto;

public class DashboardDTO {
    private long totalEmployees;
    private long availableEmployees;
    private long allocatedEmployees;
    private long totalProjects;
    private long activeProjects;
    private long totalSkills;
    private long pendingRequests;
    private long approvedRequests;
    private long completedRequests;

    private double billablePercentage;
    private double benchPercentage;

    public DashboardDTO() {
        super();
    }

	private DashboardDTO(long totalEmployees, long availableEmployees, long allocatedEmployees, long totalProjects,
			long activeProjects, long totalSkills, long pendingRequests, long approvedRequests, long completedRequests,
			double billablePercentage, double benchPercentage) {
		super();
		this.totalEmployees = totalEmployees;
		this.availableEmployees = availableEmployees;
		this.allocatedEmployees = allocatedEmployees;
		this.totalProjects = totalProjects;
		this.activeProjects = activeProjects;
		this.totalSkills = totalSkills;
		this.pendingRequests = pendingRequests;
		this.approvedRequests = approvedRequests;
		this.completedRequests = completedRequests;
		this.billablePercentage = billablePercentage;
		this.benchPercentage = benchPercentage;
	}

	public long getTotalEmployees() {
		return totalEmployees;
	}

	public void setTotalEmployees(long totalEmployees) {
		this.totalEmployees = totalEmployees;
	}

	public long getAvailableEmployees() {
		return availableEmployees;
	}

	public void setAvailableEmployees(long availableEmployees) {
		this.availableEmployees = availableEmployees;
	}

	public long getAllocatedEmployees() {
		return allocatedEmployees;
	}

	public void setAllocatedEmployees(long allocatedEmployees) {
		this.allocatedEmployees = allocatedEmployees;
	}

	public long getTotalProjects() {
		return totalProjects;
	}

	public void setTotalProjects(long totalProjects) {
		this.totalProjects = totalProjects;
	}

	public long getActiveProjects() {
		return activeProjects;
	}

	public void setActiveProjects(long activeProjects) {
		this.activeProjects = activeProjects;
	}

	public long getTotalSkills() {
		return totalSkills;
	}

	public void setTotalSkills(long totalSkills) {
		this.totalSkills = totalSkills;
	}

	public long getPendingRequests() {
		return pendingRequests;
	}

	public void setPendingRequests(long pendingRequests) {
		this.pendingRequests = pendingRequests;
	}

	public long getApprovedRequests() {
		return approvedRequests;
	}

	public void setApprovedRequests(long approvedRequests) {
		this.approvedRequests = approvedRequests;
	}

	public long getCompletedRequests() {
		return completedRequests;
	}

	public void setCompletedRequests(long completedRequests) {
		this.completedRequests = completedRequests;
	}

	public double getBillablePercentage() {
		return billablePercentage;
	}

	public void setBillablePercentage(double billablePercentage) {
		this.billablePercentage = billablePercentage;
	}

	public double getBenchPercentage() {
		return benchPercentage;
	}

	public void setBenchPercentage(double benchPercentage) {
		this.benchPercentage = benchPercentage;
	}
}