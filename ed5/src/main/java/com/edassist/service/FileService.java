package com.edassist.service;

import org.springframework.core.io.ByteArrayResource;

import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.FileContent;

public interface FileService {

	public ByteArrayResource retrieveFileById(String id) throws BadRequestException, Exception;

	public Boolean saveOrUpdateFileById(FileContent content) throws BadRequestException, Exception;

}
