package com.edassist.mongodb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.edassist.models.domain.General;
import com.edassist.mongodb.repository.custom.CustomGeneralRepository;

@Repository
public interface GeneralRepository extends MongoRepository<General, String>, CustomGeneralRepository {

	@Query(value = "{ 'component' : ?0, 'client' : ?1, 'program' : {$regex: ?2}, 'startDate' : { '$lt' : ?3 }, 'endDate' : { '$gt' : ?3 } }")
	List<General> findByComponent(String component, String client, String program, Date signedDate);

	@Query(value = "{ 'component' : ?0, 'name' : {$regex:?1, $options: 'i'}, 'client' : ?2, 'program' : {$regex: ?3} }")
	List<General> findByName(String component, String name, String client, String program);

	List<General> findByClient(String client);

	@Query(value = "{ 'client' : ?0, 'program' : {$regex: ?1} }")
	List<General> findByProgram(String client, String program);

	List<General> findByClientAndType(String client, String type);

	List<General> findByClientAndIsDeletedTrue(String client);

	List<General> findByClientAndNeedsPropagationTrue(String client);
}