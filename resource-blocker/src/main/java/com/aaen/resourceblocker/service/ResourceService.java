package com.aaen.resourceblocker.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Resource;

@Component
public interface ResourceService {

	public HttpStatus create(Resource resource);
	
	public Resource getById(String id);

	public Resource getByName(String name);
	
	public List<Resource> getAll();

	public HttpStatus deleteById(String id);

	public HttpStatus deleteAll();

	public HttpStatus update(Resource resource);
	
}
