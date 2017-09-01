package com.edassist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.edassist.models.domain.Health;
import com.edassist.mongodb.repository.HealthRepository;

@RestController
public class MonitoringController {

	@Autowired
	private HealthRepository healthRepository;

	@RequestMapping(value = "/v1/monitoring/health", method = RequestMethod.GET)
	public ResponseEntity<String> healthCheck() throws Exception {

		Health health = healthRepository.findByStatus("checkHealth");
		if (health == null) {
			throw new Exception();
		}
		return new ResponseEntity<String>("server is up", HttpStatus.OK);
	}
}