package com.edassist.service;

import java.util.List;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.Email;

public interface EmailService {

	List<Email> findByComponent(String component, String client, String program) throws BadRequestException;

	Email findByName(String Component, String name, String client, String program, boolean cascade) throws BadRequestException;

	List<Email> findByClient(String client) throws BadRequestException;

	List<Email> findByProgram(String client, String program);

	Email saveOrUpdate(Email email);

	void deleteById(String id);

	List<String> findUniqueComponents();

	List<String> findUniqueNamesForComponent(String component);

}
