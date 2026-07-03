package com.incture.erasm.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.entity.AuditLog;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.AuditLogRepository;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public AuditLog saveAudit(String action, String entityName, Long entityId, String performedBy, String description) {
        AuditLog audit = new AuditLog();

        audit.setAction(action);
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setPerformedBy(performedBy);
        audit.setActionTime(LocalDateTime.now());
        audit.setDescription(description);

        return auditLogRepository.save(audit);
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    public AuditLog getAuditLogById(Long id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit Log not found"));
    }
}