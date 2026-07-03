package com.incture.erasm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.erasm.dto.DashboardDTO;
import com.incture.erasm.service.DashboardService;

@RestController
public class DashboardController {
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping("/dashboard")
	public DashboardDTO getDashboard() {
		return dashboardService.getDashboard();
	}
}