package com.edassist.service.impl;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.edassist.dao.FileStorageDao;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.FileContent;
import com.edassist.models.domain.General;
import com.edassist.mongodb.repository.GeneralRepository;
import com.edassist.service.FileService;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileStorageDao fileStorageDao;

	@Autowired
	private GeneralRepository generalRepository;

	@Override
	public ByteArrayResource retrieveFileById(String id) throws BadRequestException, Exception {

		ByteArrayResource byteArrayResource = convertToByteArray(id);

		return byteArrayResource;
	}

	@Override
	public Boolean saveOrUpdateFileById(FileContent fileContent) throws BadRequestException, Exception {

		GridFSFile newFile = fileStorageDao.saveFile(new ByteArrayInputStream(fileContent.getFile()), fileContent.getFileName());
		General generalContent = new General();

		if (fileContent.getGeneralContentId() != null  && !fileContent.getGeneralContentId().equals("undefined")) {
			generalContent = generalRepository.findOne(fileContent.getGeneralContentId());
			if (generalContent != null) {
				if (!generalContent.getIsDeleted()) {
					generalContent.setModifiedAt(new Date());
					generalContent.setFileName(newFile.getFilename());
					generalContent.setFileId(newFile.getId().toString());
					generalContent.setNeedsPropagation(true);
				}
			}
			generalRepository.save(generalContent);
		}
		return true;
	}

	private ByteArrayResource convertToByteArray(String id) throws BadRequestException, Exception {
		GridFSDBFile file = fileStorageDao.getById(id);
		byte[] fileBytes = IOUtils.toByteArray(file.getInputStream());
		ByteArrayResource byteArrayResource = new ByteArrayResource(fileBytes);

		return byteArrayResource;
	}
}