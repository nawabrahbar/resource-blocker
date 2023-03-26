package com.aaen.resourceblocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.PrivilegeRequestResponse;

@Component
@Qualifier(value = "PrivilegeService")
public interface PrivilegeService {

	public String createPrivilege(PrivilegeRequestResponse privilege);

	public PrivilegeRequestResponse getPrivilegeById(String id);

	public List<PrivilegeRequestResponse> getPrivileges();

	public HttpStatus deletePrivilege(String id);

	public HttpStatus deletePrivileges();

	public String updatePrivilege(PrivilegeRequestResponse privilege);

}
