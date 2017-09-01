package com.edassist.mongodb.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.edassist.mongodb.repository.custom.CustomEmailRepository;

@Repository
public class EmailRepositoryImpl implements CustomEmailRepository {
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<String> findUniqueComponents() {
		@SuppressWarnings("unchecked")
		List<String> uniqueComponents = mongoTemplate.getCollection("email").distinct("component");
		return uniqueComponents;
	}

	@Override
	public List<String> findUniqueNamesForComponent(String component) {

		Query query = new Query(Criteria.where("component").is(component));
		@SuppressWarnings("unchecked")
		List<String> uniqueNames = mongoTemplate.getCollection("email").distinct("name", query.getQueryObject());
		return uniqueNames;
	}
}
