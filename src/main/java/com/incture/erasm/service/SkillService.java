package com.incture.erasm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.entity.Skill;
import com.incture.erasm.repository.SkillRepository;

@Service
public class SkillService {
	@Autowired
	private SkillRepository skillRepository;
	
	public Skill saveSkill(Skill skill) {
		return skillRepository.save(skill);
	}
	
	public List<Skill> getAllSkills(){
		return skillRepository.findAll();
	}
	
	public Skill getSkillById(Long id) {
		return skillRepository.findById(id).get();
	}
	
	public Skill updateSkill(Skill skill) {
		return skillRepository.save(skill);
	}
	
	public void deleteSkill(Long id) {
		skillRepository.deleteById(id);
	}
}