package com.incture.erasm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.erasm.entity.Role;
import com.incture.erasm.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
	public Role getRoleById(Long id) {
		return roleRepository.findById(id).get();
	}
	public Role updateRole(Role role) {
		return roleRepository.save(role);
	}
	public void deleteRole(Long id) {
		roleRepository.deleteById(id);
	}
}