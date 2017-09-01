package com.edassist.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.edassist.models.domain.ProviderDocument;

@Repository
public interface ProviderDocumentRepository extends MongoRepository<ProviderDocument, String> {

	@Query(value = "{ 'providerId' : ?0, 'providerType' : ?1 }")
	public List<ProviderDocument> findProviderDocuments(Long providerId, String providerType);

}
