package com.edassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.models.domain.ProviderDocument;
import com.edassist.service.ProviderDocumentService;

@RestController
public class ProviderDocumentController {

	@Autowired
	private ProviderDocumentService providerDocumentService;

	@RequestMapping(value = "/v1/content/provider-documents/types/{providerType}/providers/{providerId}", method = RequestMethod.GET)
	public ResponseEntity<List<ProviderDocument>> retrieveFileById(@PathVariable("providerType") String providerType, @PathVariable("providerId") Long providerId) throws Exception {

		List<ProviderDocument> providerDocumentList = providerDocumentService.retrieveProviderDocuments(providerId, providerType);
		return new ResponseEntity<List<ProviderDocument>>(providerDocumentList, HttpStatus.OK);
	}

}
