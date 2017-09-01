package com.edassist.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.edassist.dao.ProviderDocumentDao;
import com.edassist.models.domain.ProviderDocument;

@Repository
public class ProviderDocumentDaoImpl implements ProviderDocumentDao {

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public void saveOrUpdate(ProviderDocument providerDocument) {
		mongoOperations.save(providerDocument);
	}

	@Override
	public List<ProviderDocument> getAllProviderDocuments() {
		List<ProviderDocument> providerDocumentList = mongoOperations.findAll(ProviderDocument.class);
		return providerDocumentList;
	}

	@Override
	public void deleteProviderDocumentById(String id) {
		mongoOperations.remove(new Query().addCriteria((Criteria.where("_id").is(id))), ProviderDocument.class);

	}

}
