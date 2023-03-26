package com.aaen.resourceblocker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.resourceblocker.exception.RecordNotFoundException;
import com.aaen.resourceblocker.model.Privilege;
import com.aaen.resourceblocker.model.PrivilegeRequestResponse;
import com.aaen.resourceblocker.model.User;
import com.aaen.resourceblocker.service.PrivilegeService;

@RestController
@RequestMapping(path = { "/api/v1/privilege" })
public class PrivilegeController {

	@Autowired(required = true)
	private PrivilegeService privilegeService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@PostMapping
	@PreAuthorize("hasAuthority('privilege.create')")
	public ResponseEntity<?> createPrivilege(@RequestBody PrivilegeRequestResponse privilege) {
		String response = privilegeService.createPrivilege(privilege);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			return new ResponseEntity<>("Privilege creation failed", HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('privilege.get.by.id')")
	public ResponseEntity<?> getPrivilegeById(@PathVariable String id) {
		PrivilegeRequestResponse response = privilegeService.getPrivilegeById(id);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			throw new RecordNotFoundException("Invalid privilege id : " + id);
		}
	}

	@GetMapping
	@PreAuthorize("hasAuthority('privilege.get.all')")
	public ResponseEntity<?> getPrivileges() {
		List<PrivilegeRequestResponse> response = privilegeService.getPrivileges();
		if (response != null) {
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			throw new RecordNotFoundException("No records found");
		}
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('admin') and hasAuthority('privilege.delete.by.id')")
	public ResponseEntity<?> deletePrivilege(@PathVariable String id) {
		HttpStatus response = privilegeService.deletePrivilege(id);
		if (response != null) {
			return ResponseEntity.status(response).build();
		} else {
			throw new RecordNotFoundException("Invalid privilege id : " + id);
		}
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin') and hasAuthority('privilege.delete.all')")
	public ResponseEntity<?> deletePrivileges() {
		HttpStatus response = privilegeService.deletePrivileges();
		if (response != null) {
			return ResponseEntity.status(response).build();
		} else {
			throw new RecordNotFoundException("No records found");
		}
	}

	@PutMapping
	@PreAuthorize("hasAuthority('privilege.update')")
	public ResponseEntity<?> updatePrivilege(@RequestBody PrivilegeRequestResponse privilege) {
		String response = privilegeService.updatePrivilege(privilege);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		} else {
			throw new RecordNotFoundException("privilege update failed");
		}
	}

	@RequestMapping(value = "/assign-privileges", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> setPrivilegesToAllUsers() {

		String[] adminPrivileges = null;
		String[] userPrivileges = null;
		List<User> users = mongoTemplate.findAll(User.class);

		Query adminPrivilegeQuery = new Query();
		adminPrivilegeQuery.fields().include("privilege");
		adminPrivilegeQuery.with(Sort.by("privilege"));

		List<Privilege> adminDbPrivileges = mongoTemplate.find(adminPrivilegeQuery, Privilege.class);

		if (adminDbPrivileges != null && adminDbPrivileges.size() > 0) {

			adminPrivileges = new String[adminDbPrivileges.size()];

			for (int i = 0; i < adminDbPrivileges.size(); i++) {
				adminPrivileges[i] = adminDbPrivileges.get(i).getPrivilege();
			}
		}

		Query userPrivilegeQuery = new Query();
		userPrivilegeQuery.addCriteria(Criteria.where("accessibleForUser").is(true));
		userPrivilegeQuery.fields().include("privilege");
		userPrivilegeQuery.with(Sort.by("privilege"));

		List<Privilege> userDbPrivileges = mongoTemplate.find(userPrivilegeQuery, Privilege.class);

		if (userDbPrivileges != null && userDbPrivileges.size() > 0) {

			userPrivileges = new String[userDbPrivileges.size()];

			for (int i = 0; i < userDbPrivileges.size(); i++) {
				userPrivileges[i] = userDbPrivileges.get(i).getPrivilege();
			}
		}

		// Set permissions in all the users
		for (User user : users) {

			if (adminPrivileges != null && user.getRole().equals("5e7a68d4-414e-4b65-9eb9-376692c2cfaa")) {
				// For admin role
				user.setPermissions(adminPrivileges);
				mongoTemplate.save(user);

			} else if (userPrivileges != null && user.getRole().equals("9f29bfcf-04b1-418f-aafb-44909709b210")) {
				// For user roles
				user.setPermissions(userPrivileges);
				mongoTemplate.save(user);
			}
		}

		return null;
	}
}
