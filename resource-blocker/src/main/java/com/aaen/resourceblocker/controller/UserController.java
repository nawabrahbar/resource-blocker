package com.aaen.resourceblocker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.resourceblocker.exception.RecordNotFoundException;
import com.aaen.resourceblocker.model.PasswordReset;
import com.aaen.resourceblocker.model.User;
import com.aaen.resourceblocker.service.UserService;

@RestController
@RequestMapping(path = { "/api/v1/user" })
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	@PreAuthorize("hasAuthority('user.create')")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		String response = userService.createUser(user);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			return new ResponseEntity<>("user creation failed", HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('user.get.by.id')")
	public ResponseEntity<?> getUserById(@PathVariable String id, @RequestHeader(name = "Authorization") String token) {
		User user = userService.getUser(id, token);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.OK).body(user);
		} else {
			throw new RecordNotFoundException("Invalid User id : " + id);
		}
	}

	@GetMapping
	@PreAuthorize("hasAuthority('user.get.all')")
	public ResponseEntity<?> getUsers(@RequestHeader(name = "Authorization") String token) {
		List<User> userList = userService.getUsers(token);
		if (userList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(userList);
		} else {
			throw new RecordNotFoundException("No Record Found");
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('admin') and hasAuthority('user.delete.by.id')")
	public ResponseEntity<?> deleteUserById(@PathVariable String id) {
		String response = userService.deleteUser(id);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"success\":\"User deleted sucessfully\"}");
		} else {
			throw new RecordNotFoundException("Invalid User id : " + id);
		}
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('admin') and hasAuthority('user.delete.all')")
	public ResponseEntity<?> deleteAllUser() {
		String response = userService.deleteAllUsers();
		if (response != null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		} else {
			throw new RecordNotFoundException("No Record Found");
		}
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		String response = userService.updateUser(user);
		if (response != null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
		} else {
			throw new RecordNotFoundException("User update failed");
		}
	}

	@PutMapping("/change-password")
	@PreAuthorize("hasAuthority('admin') or hasAuthority('user')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordReset passwordReset,
			@RequestHeader(name = "Authorization") String token) {

		switch (userService.changePassword(passwordReset, token)) {
		case NO_CONTENT:
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		case BAD_REQUEST:
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		case UNAUTHORIZED:
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		default:
			throw new RecordNotFoundException("Password update failed");
		}
	}
}
