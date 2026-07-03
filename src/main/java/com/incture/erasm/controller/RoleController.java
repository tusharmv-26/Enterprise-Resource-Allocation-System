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

import com.incture.erasm.entity.Role;
import com.incture.erasm.service.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@PostMapping
	public Role saveRole(@RequestBody Role role) {
		return roleService.saveRole(role);
	}
	
	@GetMapping
	public List<Role> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@GetMapping("/{id}")
	public Role getRoleById(@PathVariable Long id) {
		return roleService.getRoleById(id);
	}
	
	@PutMapping("/{id}")
	public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
		role.setId(id);
		return roleService.updateRole(role);
	}
	
	@DeleteMapping("/{id}")
	public String deleteRole(@PathVariable Long id) {
		roleService.deleteRole(id);
		return "Role deleted successfully";
	}
}