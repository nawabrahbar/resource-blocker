package com.aaen.resourceblocker.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaen.resourceblocker.model.Resource;
import com.aaen.resourceblocker.service.ResourceService;
import com.aaen.resourceblocker.util.Constant;

@RestController
@RequestMapping(path = { "/api/v1/resource" })
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@PostMapping
	@PreAuthorize("hasAuthority('resource.create')")
	public ResponseEntity<?> create(@RequestBody Resource resource) {

		try {

			HttpStatus response = resourceService.create(resource);

			return ResponseEntity.status(response).body(Constant.CREATED);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('resource.get.by.id')")
	public ResponseEntity<?> getById(@PathVariable String id) {

		try {

			Resource response = resourceService.getById(id);

			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(path = "/name/{name}")
	@PreAuthorize("hasAuthority('resource.get.by.name')")
	public ResponseEntity<?> getByName(@PathVariable String name) {

		try {

			Resource response = resourceService.getByName(name);

			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping
	@PreAuthorize("hasAuthority('resource.get.all')")
	public ResponseEntity<?> getAll() {

		return ResponseEntity.ok(resourceService.getAll());
	}

	@DeleteMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('resource.delete.by.id')")
	public ResponseEntity<?> deleteById(@PathVariable String id) {

		try {

			return ResponseEntity.status(resourceService.deleteById(id)).build();

		} catch (IllegalArgumentException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping
	@PreAuthorize("hasAuthority('resource.delete.all')")
	public ResponseEntity<?> deleteAll() {

		return ResponseEntity.status(resourceService.deleteAll()).build();
	}

	@PutMapping
	@PreAuthorize("hasAuthority('resource.update')")
	public ResponseEntity<?> update(@RequestBody Resource resource) {

		return ResponseEntity.status(resourceService.update(resource)).build();
	}
}
