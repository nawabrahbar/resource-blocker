package com.aaen.resourceblocker.util;

import java.util.regex.Pattern;

public class Validation {

	public static boolean isValidPassword(String password) {

		if (password == null)
			return false;

		return Pattern.compile(Regex.PASSWORD_VALIDATION).matcher(password).matches();
	}

}
