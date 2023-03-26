package com.aaen.resourceblocker.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document(collection = "resource")
public class Resource {

	@Id
	private String id;
	private String name;
	private Integer numberOfResource;
	private String blocked;
}
