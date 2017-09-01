package com.edassist.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.edassist.models.domain.Health;

@Repository
public interface HealthRepository extends MongoRepository<Health, String> {

	@Query(value = "{ 'status' : ?0 }")
	public Health findByStatus(String status);
}