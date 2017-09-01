package com.edassist.mongodb.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.edassist.mongodb.repository.custom.CustomGeneralRepository;

@Repository
public class GeneralRepositoryImpl implements CustomGeneralRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<String> findUniqueComponents() {
		@SuppressWarnings("unchecked")
		List<String> uniqueComponents = mongoTemplate.getCollection("general").distinct("component");
		return uniqueComponents;
	}

	@Override
	public List<String> findUniqueNamesForComponent(String component) {

		Query query = new Query(Criteria.where("component").is(component));
		@SuppressWarnings("unchecked")
		List<String> uniqueNames = mongoTemplate.getCollection("general").distinct("name", query.getQueryObject());
		return uniqueNames;
	}

	@Override
	public List<String> findCollectionNames() {
		@SuppressWarnings("unchecked")
		Set<String> collections = mongoTemplate.getCollectionNames();
		return new ArrayList(collections);
	}

}
