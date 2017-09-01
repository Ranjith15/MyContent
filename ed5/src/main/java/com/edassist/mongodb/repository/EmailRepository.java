package com.edassist.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.edassist.models.domain.Email;
import com.edassist.mongodb.repository.custom.CustomEmailRepository;

@Repository
public interface EmailRepository extends MongoRepository<Email, String>, CustomEmailRepository {

	Email findById(String id);

	List<Email> findByComponentAndClientAndProgram(String component, String client, String program);

	List<Email> findByComponentAndClient(String component, String client);

	@Query(value = "{ 'component' : ?0, 'name' : {$regex:?1, $options: 'i'}, 'client' : ?2, 'program' : ?3 }")
	Email findByName(String component, String name, String client, String program);

	List<Email> findByClient(String client);

	List<Email> findByClientAndProgram(String client, String program);

	List<Email> findByClientAndIsDeletedTrue(String client);

	List<Email> findByClientAndNeedsPropagationTrue(String client);
}
