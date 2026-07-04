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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.erasm.dto.AllocationRequestDTO;
import com.incture.erasm.entity.Allocation;
import com.incture.erasm.service.AllocationService;

@RestController
@RequestMapping("/allocations")
public class AllocationController {
    @Autowired
    private AllocationService allocationService;

    @PostMapping
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public Allocation saveAllocation(@RequestBody AllocationRequestDTO dto) {
        return allocationService.saveAllocation(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public List<Allocation> getAllAllocations() {
        return allocationService.getAllAllocations();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public Allocation getAllocationById(@PathVariable Long id) {
        return allocationService.getAllocationById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public Allocation updateAllocation(@PathVariable Long id, @RequestBody Allocation allocation) {
        allocation.setId(id);
        return allocationService.updateAllocation(allocation);
    }

    @PutMapping("/release/{id}")
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public Allocation releaseEmployee(@PathVariable Long id) {
        return allocationService.releaseEmployee(id);
    }

    @PutMapping("/reallocate/{allocationId}")
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public Allocation reallocateEmployee(@PathVariable Long allocationId, @RequestParam Long projectId, @RequestParam Long requestId) {
        return allocationService.reallocateEmployee(allocationId, projectId, requestId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RESOURCE_MANAGER')")
    public String deleteAllocation(@PathVariable Long id) {
        allocationService.deleteAllocation(id);
        return "Allocation deleted successfully";
    }
}