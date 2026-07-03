package com.incture.erasm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.erasm.entity.Certification;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long>{
	
}