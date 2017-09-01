package com.edassist.service;

import com.edassist.models.domain.ProviderDocument;

import java.util.List;

public interface ProviderDocumentService {

	List<ProviderDocument> retrieveProviderDocuments(Long providerId, String providerType);

	void deleteAllProviderDocuments() throws Exception;
}
