package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.service.ResourceRequestService;

@RestController
@RequestMapping("/resource-requests")
public class ResourceRequestController {

    @Autowired
    private ResourceRequestService resourceRequestService;

    @PostMapping
    public ResourceRequest saveResourceRequest(@RequestBody ResourceRequest resourceRequest) {
        return resourceRequestService.saveResourceRequest(resourceRequest);
    }

    @GetMapping
    public List<ResourceRequest> getAllResourceRequests() {
        return resourceRequestService.getAllResourceRequests();
    }

    @GetMapping("/{id}")
    public ResourceRequest getResourceRequestById(@PathVariable Long id) {
        return resourceRequestService.getResourceRequestById(id);
    }

    @PutMapping("/{id}")
    public ResourceRequest updateResourceRequest(@PathVariable Long id,
            @RequestBody ResourceRequest resourceRequest) {

        resourceRequest.setId(id);
        return resourceRequestService.updateResourceRequest(resourceRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteResourceRequest(@PathVariable Long id) {

        resourceRequestService.deleteResourceRequest(id);

        return "Resource Request deleted successfully";
    }

    // DRAFT -> SUBMITTED
    @PutMapping("/{id}/submit")
    public ResourceRequest submitRequest(@PathVariable Long id) {
        return resourceRequestService.submitRequest(id);
    }

    // SUBMITTED -> RESOURCE_MANAGER_REVIEW
    @PutMapping("/{id}/review")
    public ResourceRequest sendForReview(@PathVariable Long id) {
        return resourceRequestService.sendForReview(id);
    }

    // RESOURCE_MANAGER_REVIEW -> APPROVED
    @PutMapping("/{id}/approve")
    public ResourceRequest approveRequest(@PathVariable Long id) {
        return resourceRequestService.approveRequest(id);
    }

    // RESOURCE_MANAGER_REVIEW -> REJECTED
    @PutMapping("/{id}/reject")
    public ResourceRequest rejectRequest(@PathVariable Long id) {
        return resourceRequestService.rejectRequest(id);
    }

    // APPROVED -> ALLOCATED
    @PutMapping("/{id}/allocate")
    public ResourceRequest markAllocated(@PathVariable Long id) {
        return resourceRequestService.markAllocated(id);
    }

    // ALLOCATED -> COMPLETED
    @PutMapping("/{id}/complete")
    public ResourceRequest completeRequest(@PathVariable Long id) {
        return resourceRequestService.completeRequest(id);
    }
}