package com.edassist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.FileContent;
import com.edassist.service.FileService;

@RestController
public class FileContentController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/v1/content/file/{id}", method = RequestMethod.GET)
	public ResponseEntity<ByteArrayResource> retrieveFileById(@PathVariable("id") String id) throws BadRequestException, Exception {

		ByteArrayResource byteArrayResource = fileService.retrieveFileById(id);

		return new ResponseEntity<ByteArrayResource>(byteArrayResource, HttpStatus.OK);

	}

	@RequestMapping(value = "/v1/content/file", method = RequestMethod.POST)
	public ResponseEntity<Boolean> saveOrUpdateFileById(@RequestBody FileContent content) throws BadRequestException, Exception {

		return new ResponseEntity<Boolean>(fileService.saveOrUpdateFileById(content), HttpStatus.OK);

	}

}
