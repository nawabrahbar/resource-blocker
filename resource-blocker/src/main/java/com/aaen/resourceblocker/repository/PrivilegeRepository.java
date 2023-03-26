package com.aaen.resourceblocker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aaen.resourceblocker.model.PrivilegeRequestResponse;

@Repository
public interface PrivilegeRepository extends MongoRepository<PrivilegeRequestResponse, String> {

}
