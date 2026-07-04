package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.service.ResourceRequestService;

@RestController
@RequestMapping("/resource-requests")
public class ResourceRequestController {
    @Autowired
    private ResourceRequestService resourceRequestService;

    @PostMapping
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest saveResourceRequest(@RequestBody ResourceRequest resourceRequest) {
        return resourceRequestService.saveResourceRequest(resourceRequest);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public List<ResourceRequest> getAllResourceRequests() {
        return resourceRequestService.getAllResourceRequests();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest getResourceRequestById(@PathVariable Long id) {
        return resourceRequestService.getResourceRequestById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest updateResourceRequest(@PathVariable Long id, @RequestBody ResourceRequest resourceRequest) {
        resourceRequest.setId(id);
        return resourceRequestService.updateResourceRequest(resourceRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public String deleteResourceRequest(@PathVariable Long id) {
        resourceRequestService.deleteResourceRequest(id);
        return "Resource Request deleted successfully";
    }

    @PutMapping("/{id}/submit")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest submitRequest(@PathVariable Long id) {
        return resourceRequestService.submitRequest(id);
    }

    @PutMapping("/{id}/review")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest sendForReview(@PathVariable Long id) {
        return resourceRequestService.sendForReview(id);
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest approveRequest(@PathVariable Long id) {
        return resourceRequestService.approveRequest(id);
    }
    
    @PutMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest rejectRequest(@PathVariable Long id) {
        return resourceRequestService.rejectRequest(id);
    }

    @PutMapping("/{id}/allocate")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest markAllocated(@PathVariable Long id) {
        return resourceRequestService.markAllocated(id);
    }

    @PutMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('PROJECT_MANAGER','RESOURCE_MANAGER')")
    public ResourceRequest completeRequest(@PathVariable Long id) {
        return resourceRequestService.completeRequest(id);
    }
}