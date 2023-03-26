package com.aaen.resourceblocker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "blacklistedJwtToken")
public class BlacklistedJwtToken {

	@Id
	private String id;
	private String token;
}
