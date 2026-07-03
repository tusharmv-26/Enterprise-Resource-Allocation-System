package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import com.incture.erasm.dto.CertificationRequestDTO;
import com.incture.erasm.entity.Certification;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.CertificationRepository;
import com.incture.erasm.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CertificationServiceTest {
    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private CertificationService certificationService;

    private Employee employee;
    private Certification certification;
    private CertificationRequestDTO dto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId(1L);

        certification = new Certification();
        certification.setId(1L);
        certification.setCertificationName("AWS Associate");
        certification.setIssuingOrganization("Amazon");
        certification.setIssueDate(LocalDate.now());
        certification.setExpiryDate(LocalDate.now().plusYears(3));
        certification.setEmployee(employee);

        dto = new CertificationRequestDTO();
        dto.setEmployeeId(1L);
        dto.setCertificationName("AWS Associate");
        dto.setIssuingOrganization("Amazon");
        dto.setIssueDate(LocalDate.now());
        dto.setExpiryDate(LocalDate.now().plusYears(3));
    }

    @Test
    void testSaveCertificationSuccess() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(certificationRepository.save(any(Certification.class)))
                .thenReturn(certification);

        Certification saved =
                certificationService.saveCertification(dto);

        assertNotNull(saved);
        assertEquals("AWS Associate",
                saved.getCertificationName());

        verify(employeeRepository).findById(1L);
        verify(certificationRepository)
                .save(any(Certification.class));
    }

    @Test
    void testSaveCertificationEmployeeNotFound() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> certificationService.saveCertification(dto));

        verify(certificationRepository, never())
                .save(any());
    }

    @Test
    void testGetAllCertifications() {
        when(certificationRepository.findAll())
                .thenReturn(Arrays.asList(certification));

        assertEquals(1,
                certificationService.getAllCertifications().size());

        verify(certificationRepository).findAll();
    }

    @Test
    void testGetAllCertificationsEmpty() {
        when(certificationRepository.findAll())
                .thenReturn(Collections.emptyList());

        assertTrue(
                certificationService.getAllCertifications().isEmpty());
    }

    @Test
    void testGetCertificationByIdSuccess() {
        when(certificationRepository.findById(1L))
                .thenReturn(Optional.of(certification));

        Certification found =
                certificationService.getCertificationById(1L);

        assertEquals(1L, found.getId());

        verify(certificationRepository).findById(1L);
    }

    @Test
    void testGetCertificationByIdNotFound() {
        when(certificationRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> certificationService.getCertificationById(10L));
    }

    @Test
    void testUpdateCertificationSuccess() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));

        when(certificationRepository.save(certification))
                .thenReturn(certification);

        Certification updated =
                certificationService.updateCertification(certification);

        assertNotNull(updated);

        verify(employeeRepository).findById(1L);
        verify(certificationRepository).save(certification);
    }

    @Test
    void testUpdateCertificationEmployeeNotFound() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> certificationService.updateCertification(certification));

        verify(certificationRepository, never()).save(any());
    }

    @Test
    void testDeleteCertification() {
        doNothing().when(certificationRepository)
                .deleteById(1L);

        certificationService.deleteCertification(1L);

        verify(certificationRepository)
                .deleteById(1L);
    }

    @Test
    void testRepositoryInteractions() {
        when(certificationRepository.findAll())
                .thenReturn(Collections.emptyList());

        certificationService.getAllCertifications();

        verify(certificationRepository).findAll();
        verifyNoMoreInteractions(certificationRepository);
    }
}