package com.aaen.resourceblocker.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.resourceblocker.model.Resource;

@Repository
public interface ResourceRepository extends MongoRepository<Resource, String>, ResourceCustomRepository {

	public Optional<Resource> findByName(String token);
	
	public boolean existsByName(String name);

}
