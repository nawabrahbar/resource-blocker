package com.aaen.resourceblocker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.PasswordReset;
import com.aaen.resourceblocker.model.User;

@Component
@Qualifier(value = "UserService")
public interface UserService {

	public String createUser(User user);

	public User getUser(String id, String token);

	public List<User> getUsers(String token);

	public String deleteUser(String id);

	public String deleteAllUsers();

	public String updateUser(User user);

	public HttpStatus changePassword(PasswordReset passwordReset, String token);

}
