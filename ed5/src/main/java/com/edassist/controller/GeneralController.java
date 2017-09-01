package com.edassist.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.exception.BadRequestException;
import com.edassist.exception.NotFoundException;
import com.edassist.models.domain.General;
import com.edassist.service.GeneralService;

@RestController
public class GeneralController {

	private final GeneralService generalService;

	@Autowired
	public GeneralController(GeneralService generalService) {
		this.generalService = generalService;
	}

	@RequestMapping(value = "/v1/content/general/components/{component}", method = RequestMethod.GET)
	public ResponseEntity<List<General>> findByComponentCategory(@PathVariable(value = "component") String component, @RequestParam(value = "client", required = false) String client,
			@RequestParam(value = "program", required = false) String program, @RequestParam(value = "locale", required = false, defaultValue = "en") String locale,
			@RequestParam(value = "textOnly", required = false, defaultValue = "true") boolean textOnly, @RequestParam(value = "signedDate", required = false) Date signedDate) {

		List<General> contents = generalService.findByComponent(component, client, program, textOnly, signedDate);

		return new ResponseEntity<>(contents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/components/{component}/names/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<General>> findByName(@PathVariable(value = "component") String component, @PathVariable(value = "name") String name,
			@RequestParam(value = "client", required = false) String client, @RequestParam(value = "program", required = false) String program,
			@RequestParam(value = "locale", required = false, defaultValue = "en") String locale, @RequestParam(value = "textOnly", required = false, defaultValue = "true") boolean textOnly,
			@RequestParam(value = "cascade", required = false, defaultValue = "true") boolean cascade)
			throws NotFoundException, DataAccessException, Exception {

		List<General> contents = generalService.findByName(component, name, client, program, textOnly, cascade);
		return new ResponseEntity<>(contents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/clients/{client}", method = RequestMethod.GET)
	public ResponseEntity<List<General>> findByClient(@PathVariable(value = "client") String client, @RequestParam(value = "textOnly", required = false, defaultValue = "true") boolean textOnly) {

		List<General> contents = generalService.findByClient(client, textOnly);
		return new ResponseEntity<>(contents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/programs/{program}", method = RequestMethod.GET)
	public ResponseEntity<List<General>> findByProgram(@PathVariable(value = "program") String program, @RequestParam(value = "client", required = false) String client,
			@RequestParam(value = "textOnly", required = false, defaultValue = "true") boolean textOnly) {

		List<General> contents = generalService.findByProgram(client, program, textOnly);
		return new ResponseEntity<>(contents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general", method = RequestMethod.POST)
	public ResponseEntity<General> saveContent(@RequestBody General content) throws BadRequestException, Exception {

		General insertedContent = generalService.saveOrUpdate(content);
		return new ResponseEntity<>(insertedContent, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/{id}", method = RequestMethod.PUT)
	public ResponseEntity<General> updateContent(@PathVariable(value = "id") String id, @RequestBody General content) throws BadRequestException, Exception {

		General updatedContent = generalService.saveOrUpdate(content);
		return new ResponseEntity<>(updatedContent, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteContent(@PathVariable(value = "id") String id) throws Exception {

		generalService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/components/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findDistinctComponents() throws NotFoundException, DataAccessException, Exception {

		List<String> distinctComponents = generalService.findUniqueComponents();
		return new ResponseEntity<>(distinctComponents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/general/components/{component}/names/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findDistinctNamesforComponent(@PathVariable String component) throws NotFoundException, DataAccessException, Exception {

		List<String> distinctNames = generalService.findUniqueNamesForComponent(component);
		return new ResponseEntity<>(distinctNames, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/collection/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findCollectionNames() throws NotFoundException, DataAccessException, Exception {

		List<String> collections = generalService.findCollectionNames();
		return new ResponseEntity<>(collections, HttpStatus.OK);
	}

}
