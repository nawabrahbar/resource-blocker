package com.aaen.resourceblocker.model;

import lombok.Data;

@Data
public class PasswordReset {

	private String currentPassword;
	private String newPassword;
}
