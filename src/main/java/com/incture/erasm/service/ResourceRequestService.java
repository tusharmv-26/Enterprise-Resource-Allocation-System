package com.incture.erasm.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.incture.erasm.entity.Project;
import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.enums.RequestStatus;
import com.incture.erasm.exception.ProjectNotFoundException;
import com.incture.erasm.exception.ResourceNotFoundException;
import com.incture.erasm.repository.ProjectRepository;
import com.incture.erasm.repository.ResourceRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ResourceRequestService {
    private static final Logger logger = LoggerFactory.getLogger(ResourceRequestService.class);

    @Autowired
    private ResourceRequestRepository resourceRequestRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuditLogService auditLogService;

    public ResourceRequest saveResourceRequest(ResourceRequest resourceRequest) {
        Long projectId = resourceRequest.getProject().getId();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with ID: " + projectId));
        resourceRequest.setProject(project);
        resourceRequest.setStatus(RequestStatus.DRAFT);
        ResourceRequest savedRequest = resourceRequestRepository.save(resourceRequest);
        logger.info("Resource Request created successfully. ID: {}", savedRequest.getId());
        auditLogService.saveAudit(
                "CREATE",
                "ResourceRequest",
                savedRequest.getId(),
                "SYSTEM",
                "Resource request created successfully");
        return savedRequest;
    }

    public List<ResourceRequest> getAllResourceRequests() {
        return resourceRequestRepository.findAll();
    }

    public ResourceRequest getResourceRequestById(Long id) {
        return resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));
    }

    public ResourceRequest updateResourceRequest(ResourceRequest resourceRequest) {
        ResourceRequest updatedRequest = resourceRequestRepository.save(resourceRequest);
        logger.info("Resource Request updated successfully. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "UPDATE",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request updated successfully");
        return updatedRequest;
    }

    public void deleteResourceRequest(Long id) {
        logger.info("Deleting Resource Request. ID: {}", id);
        resourceRequestRepository.deleteById(id);
        auditLogService.saveAudit(
                "DELETE",
                "ResourceRequest",
                id,
                "SYSTEM",
                "Resource request deleted successfully");
    }

    // DRAFT -> SUBMITTED
    public ResourceRequest submitRequest(Long id) {
        ResourceRequest request = resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));

        request.setStatus(RequestStatus.SUBMITTED);
        ResourceRequest updatedRequest = resourceRequestRepository.save(request);
        logger.info("Resource Request submitted successfully. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "SUBMIT",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request submitted");
        return updatedRequest;
    }

    // SUBMITTED -> RESOURCE_MANAGER_REVIEW
    public ResourceRequest sendForReview(Long id) {
        ResourceRequest request = resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));

        request.setStatus(RequestStatus.RESOURCE_MANAGER_REVIEW);
        ResourceRequest updatedRequest = resourceRequestRepository.save(request);
        logger.info("Resource Request sent for review. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "REVIEW",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request sent for review");
        return updatedRequest;
    }

    // REVIEW -> APPROVED
    public ResourceRequest approveRequest(Long id) {
        ResourceRequest request = resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));
        request.setStatus(RequestStatus.APPROVED);
        ResourceRequest updatedRequest = resourceRequestRepository.save(request);
        logger.info("Resource Request approved successfully. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "APPROVE",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request approved");
        return updatedRequest;
    }

    // REVIEW -> REJECTED
    public ResourceRequest rejectRequest(Long id) {
        ResourceRequest request = resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));
        request.setStatus(RequestStatus.REJECTED);
        ResourceRequest updatedRequest = resourceRequestRepository.save(request);
        logger.info("Resource Request rejected. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "REJECT",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request rejected");
        return updatedRequest;
    }

    // APPROVED -> ALLOCATED
    public ResourceRequest markAllocated(Long id) {
        ResourceRequest request = resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));
        request.setStatus(RequestStatus.ALLOCATED);
        ResourceRequest updatedRequest = resourceRequestRepository.save(request);
        logger.info("Resource Request allocated successfully. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "ALLOCATE",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request allocated");
        return updatedRequest;
    }

    // ALLOCATED -> COMPLETED
    public ResourceRequest completeRequest(Long id) {
        ResourceRequest request = resourceRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource Request not found"));

        request.setStatus(RequestStatus.COMPLETED);
        ResourceRequest updatedRequest = resourceRequestRepository.save(request);
        logger.info("Resource Request completed successfully. ID: {}", updatedRequest.getId());
        auditLogService.saveAudit(
                "COMPLETE",
                "ResourceRequest",
                updatedRequest.getId(),
                "SYSTEM",
                "Resource request completed");
        return updatedRequest;
    }
}