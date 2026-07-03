package com.incture.erasm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.dto.CertificationRequestDTO;
import com.incture.erasm.entity.Certification;
import com.incture.erasm.entity.Employee;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.CertificationRepository;
import com.incture.erasm.repository.EmployeeRepository;

@Service
public class CertificationService {
    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Certification saveCertification(CertificationRequestDTO dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Certification certification = new Certification();

        certification.setCertificationName(dto.getCertificationName());
        certification.setIssuingOrganization(dto.getIssuingOrganization());
        certification.setIssueDate(dto.getIssueDate());
        certification.setExpiryDate(dto.getExpiryDate());
        certification.setEmployee(employee);

        return certificationRepository.save(certification);
    }

    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    public Certification getCertificationById(Long id) {
        return certificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certification not found"));
    }

    public Certification updateCertification(Certification certification) {
        Long employeeId = certification.getEmployee().getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        certification.setEmployee(employee);
        return certificationRepository.save(certification);
    }

    public void deleteCertification(Long id) {
        certificationRepository.deleteById(id);
    }
}