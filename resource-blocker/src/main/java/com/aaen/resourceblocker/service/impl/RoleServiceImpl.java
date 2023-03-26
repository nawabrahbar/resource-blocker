package com.aaen.resourceblocker.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaen.resourceblocker.model.Role;
import com.aaen.resourceblocker.repository.RoleRepository;
import com.aaen.resourceblocker.service.RoleService;
import com.aaen.resourceblocker.util.Constant;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository repository;

	@Override
	public String createRole(Role role) {
		
		role.setId(UUID.randomUUID().toString());
		
		repository.save(role);
		
		return Constant.CREATED;
	}

	@Override
	public Role getRoleById(String id) {
		
		Optional<Role> optional = repository.findById(id);
		
		return optional.get();
	}

	@Override
	public Role getRoleByName(String name) {
		
		Optional<Role> optional = repository.findByName(name);
		
		return optional.get();
	}

	@Override
	public List<Role> getRoles() {
		
		return repository.findAll();
	}

	@Override
	public String deleteRoleById(String id) {
		
		repository.deleteById(id);
		
		return Constant.DELETED;
	}

	@Override
	public String deleteRoles() {
		
		repository.deleteAll();
		
		return Constant.DELETED;
	}

	@Override
	public String updateRole(Role role) {

		Role dbRole = getRoleById(role.getId());

		if (Objects.nonNull(dbRole)) {
			
			if (dbRole.getName() != null)
				dbRole.setName(role.getName());

			repository.save(dbRole);

			return Constant.UPDATED;
		}
		
		return Constant.RECORD_NOT_FOUND;
	}

}
