package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.incture.erasm.entity.AuditLog;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.AuditLogRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuditLogServiceTest {
    @Mock
    private AuditLogRepository auditLogRepository;

    @InjectMocks
    private AuditLogService auditLogService;

    private AuditLog auditLog;

    @BeforeEach
    void setUp() {
        auditLog = new AuditLog();

        auditLog.setId(1L);
        auditLog.setAction("CREATE");
        auditLog.setEntityName("Employee");
        auditLog.setEntityId(101L);
        auditLog.setPerformedBy("Admin");
        auditLog.setDescription("Employee created");
    }

    @Test
    void testSaveAuditSuccess() {
        when(auditLogRepository.save(any(AuditLog.class)))
                .thenReturn(auditLog);

        AuditLog saved = auditLogService.saveAudit(
                "CREATE",
                "Employee",
                101L,
                "Admin",
                "Employee created");

        assertNotNull(saved);
        assertEquals("CREATE", saved.getAction());
        assertEquals("Employee", saved.getEntityName());

        verify(auditLogRepository).save(any(AuditLog.class));
    }

    @Test
    void testGetAllAuditLogs() {
        when(auditLogRepository.findAll())
                .thenReturn(Arrays.asList(auditLog));

        List<AuditLog> logs = auditLogService.getAllAuditLogs();

        assertEquals(1, logs.size());
        assertEquals("CREATE", logs.get(0).getAction());

        verify(auditLogRepository).findAll();
    }

    @Test
    void testGetAllAuditLogsEmpty() {
        when(auditLogRepository.findAll())
                .thenReturn(Arrays.asList());

        List<AuditLog> logs = auditLogService.getAllAuditLogs();

        assertTrue(logs.isEmpty());
    }

    @Test
    void testGetAuditLogByIdSuccess() {
        when(auditLogRepository.findById(1L))
                .thenReturn(Optional.of(auditLog));

        AuditLog result = auditLogService.getAuditLogById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("CREATE", result.getAction());

        verify(auditLogRepository).findById(1L);
    }

    @Test
    void testGetAuditLogByIdNotFound() {
        when(auditLogRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> auditLogService.getAuditLogById(10L));

        verify(auditLogRepository).findById(10L);
    }

    @Test
    void testRepositoryInteractions() {
        when(auditLogRepository.findAll())
                .thenReturn(Arrays.asList());

        auditLogService.getAllAuditLogs();

        verify(auditLogRepository, times(1)).findAll();
        verifyNoMoreInteractions(auditLogRepository);
    }

    @Test
    void testSaveAuditCreatesActionTime() {
        when(auditLogRepository.save(any(AuditLog.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AuditLog saved = auditLogService.saveAudit(
                "UPDATE",
                "Project",
                5L,
                "Manager",
                "Project updated");

        assertNotNull(saved.getActionTime());
        assertEquals("UPDATE", saved.getAction());
        assertEquals("Project", saved.getEntityName());
    }
}