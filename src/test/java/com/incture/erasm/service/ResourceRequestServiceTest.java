package com.incture.erasm.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incture.erasm.entity.Project;
import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.enums.RequestStatus;
import com.incture.erasm.repository.ProjectRepository;
import com.incture.erasm.repository.ResourceRequestRepository;

@ExtendWith(MockitoExtension.class)
class ResourceRequestServiceTest {
    @Mock
    private ResourceRequestRepository resourceRequestRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private ResourceRequestService resourceRequestService;

    @Test
    void testSaveResourceRequest() {
        Project project = new Project();
        project.setId(1L);

        ResourceRequest request = new ResourceRequest();
        request.setId(1L);
        request.setProject(project);

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequest result =
                resourceRequestService.saveResourceRequest(request);

        assertNotNull(result);
        assertEquals(RequestStatus.DRAFT, result.getStatus());

        verify(projectRepository).findById(1L);
        verify(resourceRequestRepository).save(any(ResourceRequest.class));
    }

    @Test
    void testApproveRequest() {
        ResourceRequest request = new ResourceRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.RESOURCE_MANAGER_REVIEW);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequest result =
                resourceRequestService.approveRequest(1L);

        assertEquals(RequestStatus.APPROVED, result.getStatus());

        verify(resourceRequestRepository).findById(1L);
        verify(resourceRequestRepository).save(request);
    }

    @Test
    void testRejectRequest() {
        ResourceRequest request = new ResourceRequest();
        request.setId(1L);
        request.setStatus(RequestStatus.RESOURCE_MANAGER_REVIEW);

        when(resourceRequestRepository.findById(1L))
                .thenReturn(Optional.of(request));

        when(resourceRequestRepository.save(any(ResourceRequest.class)))
                .thenReturn(request);

        ResourceRequest result =
                resourceRequestService.rejectRequest(1L);

        assertEquals(RequestStatus.REJECTED, result.getStatus());

        verify(resourceRequestRepository).findById(1L);
        verify(resourceRequestRepository).save(request);
    }
}