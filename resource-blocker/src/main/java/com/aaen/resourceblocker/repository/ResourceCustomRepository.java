package com.aaen.resourceblocker.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aaen.resourceblocker.model.Resource;

@Component
public interface ResourceCustomRepository {

	public List<Resource> findAllWithSortByName();
}
