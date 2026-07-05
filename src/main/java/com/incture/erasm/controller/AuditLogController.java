package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.incture.erasm.entity.AuditLog;
import com.incture.erasm.service.AuditLogService;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public List<AuditLog> getAllAuditLogs() {
        return auditLogService.getAllAuditLogs();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public AuditLog getAuditLogById(@PathVariable Long id) {
        return auditLogService.getAuditLogById(id);
    }
}