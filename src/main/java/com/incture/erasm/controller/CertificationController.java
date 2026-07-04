package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.erasm.dto.CertificationRequestDTO;
import com.incture.erasm.entity.Certification;
import com.incture.erasm.service.CertificationService;

@RestController
@RequestMapping("/certifications")
public class CertificationController {
    @Autowired
    private CertificationService certificationService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Certification saveCertification(@RequestBody CertificationRequestDTO dto) {
        return certificationService.saveCertification(dto);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Certification> getAllCertifications() {
        return certificationService.getAllCertifications();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Certification getCertificationById(@PathVariable Long id) {
        return certificationService.getCertificationById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public Certification updateCertification(@PathVariable Long id, @RequestBody Certification certification) {

        certification.setId(id);

        return certificationService.updateCertification(certification);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteCertification(@PathVariable Long id) {
        certificationService.deleteCertification(id);
        return "Certification deleted successfully";
    }
}