package com.edassist.dao;

import java.util.List;

import com.edassist.models.domain.ProviderDocument;

public interface ProviderDocumentDao {

	public void saveOrUpdate(ProviderDocument providerDocument);

	public List<ProviderDocument> getAllProviderDocuments();

	public void deleteProviderDocumentById(String id);

}
