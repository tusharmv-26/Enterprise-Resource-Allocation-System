package com.incture.erasm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.erasm.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{
	
}