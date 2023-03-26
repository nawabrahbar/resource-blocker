package com.aaen.resourceblocker.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.aaen.resourceblocker.exception.RecordNotFoundException;
import com.aaen.resourceblocker.model.Resource;
import com.aaen.resourceblocker.repository.ResourceRepository;
import com.aaen.resourceblocker.service.ResourceService;
import com.aaen.resourceblocker.util.Constant;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository repository;

	/**
	 * @param resource
	 * @return CREATED - resource created, NO_CONTENT - resource already exist, INTERNAL_SERVER_ERROR - Exception
	 */
	@Override
	public HttpStatus create(Resource resource) {

		try {
			
			resource.setName(resource.getName().toLowerCase());
			
			if(!repository.existsByName(resource.getName())) {
			
				resource.setId(UUID.randomUUID().toString());

				repository.save(resource);

				return HttpStatus.CREATED;
			}
			
			return HttpStatus.NO_CONTENT;

		} catch (IllegalArgumentException e) {

			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

	@Override
	public Resource getById(String id) {

		try {

			Optional<Resource> optional = repository.findById(id);

			if (optional.isPresent())
				return optional.get();

			else
				throw new RecordNotFoundException(Constant.RECORD_NOT_FOUND);

		} catch (IllegalArgumentException e) {

			throw new RecordNotFoundException(Constant.RECORD_NOT_FOUND);
		}
	}

	@Override
	public Resource getByName(String name) {

		try {

			Optional<Resource> optional = repository.findByName(name.toLowerCase());

			if (optional.isPresent())
				return optional.get();

			else
				throw new RecordNotFoundException(Constant.RECORD_NOT_FOUND);

		} catch (IllegalArgumentException e) {

			throw new RecordNotFoundException(Constant.RECORD_NOT_FOUND);
		}
	}

	@Override
	public List<Resource> getAll() {

		return repository.findAllWithSortByName();
	}

	@Override
	public HttpStatus deleteById(String id) {

		try {

			repository.deleteById(id);

			return HttpStatus.NO_CONTENT;

		} catch (IllegalArgumentException e) {

			throw new RecordNotFoundException(Constant.RECORD_NOT_FOUND);
		}
	}

	@Override
	public HttpStatus deleteAll() {

		repository.deleteAll();

		return HttpStatus.NO_CONTENT;
	}

	@Override
	public HttpStatus update(Resource resource) {

		Resource update = getById(resource.getId());

		if (Objects.nonNull(resource)) {

			if (Objects.nonNull(resource.getName())) {
				update.setName(resource.getName().toLowerCase());
			}

			if (Objects.nonNull(resource.getNumberOfResource())) {
				update.setNumberOfResource(resource.getNumberOfResource());
			}
			
			if (Objects.nonNull(resource.getBlocked())) {
				update.setBlocked(resource.getBlocked());
			}
			
			repository.save(update);
			
			return HttpStatus.NO_CONTENT;
		}
		
		return HttpStatus.NOT_FOUND;
	}
}
