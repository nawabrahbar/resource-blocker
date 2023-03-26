package com.aaen.resourceblocker.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Resource;
import com.aaen.resourceblocker.repository.ResourceCustomRepository;
import com.aaen.resourceblocker.util.Constant;


@Component
public class ResourceCustomRepositoryImpl implements ResourceCustomRepository {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public List<Resource> findAllWithSortByName(){
		
		return mongoTemplate.find(new Query().with(Sort.by(Sort.Direction.ASC, Constant.NAME)), Resource.class);		
	}
	
	
}
