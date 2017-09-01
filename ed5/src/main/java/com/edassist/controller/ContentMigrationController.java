package com.edassist.controller;

import com.edassist.exception.BadRequestException;
import com.edassist.service.MigrationService;
import com.edassist.service.ProviderDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentMigrationController {

	private final ProviderDocumentService providerDocumentService;
	private final MigrationService migrationService;

	@Autowired
	public ContentMigrationController(ProviderDocumentService providerDocumentService, MigrationService migrationService) {
		this.providerDocumentService = providerDocumentService;
		this.migrationService = migrationService;
	}

	@RequestMapping(value = "/v1/content-migration/migrate-client", method = RequestMethod.POST)
	public ResponseEntity<Void> migrateClient(@RequestParam String clientId) throws Exception {
		migrationService.migrateClient(clientId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content-migration/delete-all-provider-documents", method = RequestMethod.POST)
	public void deleteAllProviderDocuemnts() throws Exception {

		providerDocumentService.deleteAllProviderDocuments();
	}

}
