package com.incture.erasm.dto;

public class ResourceUtilizationReportDTO {
	 private String employeeCode;
	 private String employeeName;
	 private Integer totalHours;
	 private Integer billableHours;
	 private Integer benchHours;
	 private Double billablePercentage;
	 private Double benchPercentage;
	 
	 public ResourceUtilizationReportDTO() {
			super();
	 }

	 public ResourceUtilizationReportDTO(String employeeCode, String employeeName, Integer totalHours,
			Integer billableHours, Integer benchHours, Double billablePercentage, Double benchPercentage) {
		super();
		this.employeeCode = employeeCode;
		this.employeeName = employeeName;
		this.totalHours = totalHours;
		this.billableHours = billableHours;
		this.benchHours = benchHours;
		this.billablePercentage = billablePercentage;
		this.benchPercentage = benchPercentage;
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

	 public Double getBillablePercentage() {
		 return billablePercentage;
	 }

	 public void setBillablePercentage(Double billablePercentage) {
		 this.billablePercentage = billablePercentage;
	 }

	 public Double getBenchPercentage() {
		 return benchPercentage;
	 }

	 public void setBenchPercentage(Double benchPercentage) {
		 this.benchPercentage = benchPercentage;
	 }
}