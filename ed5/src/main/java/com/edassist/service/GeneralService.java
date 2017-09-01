package com.edassist.service;

import java.util.Date;
import java.util.List;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.General;

public interface GeneralService {

	List<General> findByName(String Component, String name, String client, String program, boolean textOnly, boolean cascade) throws BadRequestException;

	List<General> findByComponent(String component, String client, String program, boolean textOnly, Date signedDate) throws BadRequestException;

	List<General> findByClient(String client, boolean textOnly) throws BadRequestException;

	List<General> findByProgram(String client, String program, boolean textOnly);

	General saveOrUpdate(General content);

	void deleteById(String id);

	List<String> findUniqueComponents();

	List<String> findUniqueNamesForComponent(String component);

	List<String> findCollectionNames();
}
