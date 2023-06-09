package com.aaen.resourceblocker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "user")
public class User {

	@Id
	private String id;
	private String email;
	private String password;
	private String name;
	private String role;
	private String[] permissions;
}
