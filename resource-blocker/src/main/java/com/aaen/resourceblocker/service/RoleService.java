package com.aaen.resourceblocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Role;

@Component
@Qualifier(value = "RoleService")
public interface RoleService {

	public String createRole(Role role);

	public Role getRoleById(String id);

	public Role getRoleByName(String name);

	public List<Role> getRoles();

	public String deleteRoleById(String id);

	public String deleteRoles();

	public String updateRole(Role role);

}
