package com.edassist.controller;

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
import com.edassist.models.domain.Email;
import com.edassist.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/v1/content/email/components/{component}", method = RequestMethod.GET)
	public ResponseEntity<List<Email>> findByComponentCategory(@PathVariable(value = "component") String component, @RequestParam(value = "client", required = false) String client,
			@RequestParam(value = "program", required = false) String program, @RequestParam(value = "locale", required = false, defaultValue = "en") String locale)
			throws NotFoundException, DataAccessException, Exception {

		List<Email> contents = emailService.findByComponent(component, client, program);
		return new ResponseEntity<List<Email>>(contents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/components/{component}/names/{name}", method = RequestMethod.GET)
	public ResponseEntity<Email> findByName(@PathVariable(value = "component") String component, @PathVariable(value = "name") String name,
			@RequestParam(value = "client", required = false, defaultValue = "global") String client, @RequestParam(value = "program", required = false) String program,
			@RequestParam(value = "locale", required = false, defaultValue = "en") String locale, @RequestParam(value = "cascade", required = false, defaultValue = "true") boolean cascade) throws NotFoundException, DataAccessException, Exception {

		Email content = emailService.findByName(component, name, client, program, cascade);
		return new ResponseEntity<>(content, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/clients/{client}", method = RequestMethod.GET)
	public ResponseEntity<List<Email>> findByClient(@PathVariable(value = "client") String client) {
		List<Email> emails = emailService.findByClient(client);
		return new ResponseEntity<>(emails, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/programs/{program}", method = RequestMethod.GET)
	public ResponseEntity<List<Email>> findByProgram(@PathVariable(value = "program") String program, @RequestParam(value = "client", required = false) String client) {
		List<Email> emails = emailService.findByProgram(client, program);
		return new ResponseEntity<>(emails, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email", method = RequestMethod.POST)
	public ResponseEntity<Email> saveEmail(@RequestBody Email email) throws BadRequestException, Exception {

		Email updatedEmail = emailService.saveOrUpdate(email);
		return new ResponseEntity<Email>(updatedEmail, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/{emailId}", method = RequestMethod.PUT)
	public ResponseEntity<Email> updateEmail(@PathVariable(value = "emailId") String emailId, @RequestBody Email email) throws BadRequestException, Exception {

		Email updatedEmail = emailService.saveOrUpdate(email);
		return new ResponseEntity<Email>(updatedEmail, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/{emailId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteEmail(@PathVariable(value = "emailId") String emailId) throws Exception {

		emailService.deleteById(emailId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/components/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findDistinctComponents() throws NotFoundException, DataAccessException, Exception {

		List<String> distinctComponents = emailService.findUniqueComponents();
		return new ResponseEntity<List<String>>(distinctComponents, HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/components/{component}/names/keys", method = RequestMethod.GET)
	public ResponseEntity<List<String>> findDistinctNamesforComponent(@PathVariable String component) throws NotFoundException, DataAccessException, Exception {

		List<String> distinctNames = emailService.findUniqueNamesForComponent(component);
		return new ResponseEntity<List<String>>(distinctNames, HttpStatus.OK);
	}

}
