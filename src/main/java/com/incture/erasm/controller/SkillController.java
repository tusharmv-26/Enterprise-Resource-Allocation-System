package com.incture.erasm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incture.erasm.entity.Skill;
import com.incture.erasm.service.SkillService;

@RestController
@RequestMapping("/skills")
public class SkillController {
	@Autowired
	private SkillService skillService;
	
	@PostMapping
	public Skill saveSkill(@RequestBody Skill skill) {
		return skillService.saveSkill(skill);
	}
	
	@GetMapping
	public List<Skill> getAllSkills(){
		return skillService.getAllSkills();
	}
	
	@GetMapping("/{id}")
	public Skill getSkillById(@PathVariable Long id) {
		return skillService.getSkillById(id);
	}
	
	@PutMapping("/{id}")
	public Skill updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
		skill.setId(id);
		return skillService.updateSkill(skill);
	}
	
	@DeleteMapping
	public String deleteSkill(@PathVariable Long id) {
		skillService.deleteSkill(id);
		return "Skill deleted successfully";
	}
}