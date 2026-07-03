package com.incture.erasm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.erasm.entity.ResourceRequest;
import com.incture.erasm.enums.RequestStatus;

@Repository
public interface ResourceRequestRepository extends JpaRepository<ResourceRequest, Long>{
	long countByStatus(RequestStatus status);
}