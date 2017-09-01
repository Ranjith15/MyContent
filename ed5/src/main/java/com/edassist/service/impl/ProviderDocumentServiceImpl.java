package com.edassist.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.edassist.dao.FileStorageDao;
import com.edassist.dao.ProviderDocumentDao;
import com.edassist.utils.CommonUtil;
import com.mongodb.gridfs.GridFSFile;
import org.apache.tika.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edassist.models.domain.ProviderDocument;
import com.edassist.mongodb.repository.ProviderDocumentRepository;
import com.edassist.service.ProviderDocumentService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ProviderDocumentServiceImpl implements ProviderDocumentService {

	private final ProviderDocumentRepository providerDocumentRepository;
	private final ProviderDocumentDao providerDocumentDao;
	private final FileStorageDao fileStorageDao;
	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	public ProviderDocumentServiceImpl(ProviderDocumentRepository providerDocumentRepository, ProviderDocumentDao providerDocumentDao, FileStorageDao fileStorageDao) {
		this.providerDocumentRepository = providerDocumentRepository;
		this.providerDocumentDao = providerDocumentDao;
		this.fileStorageDao = fileStorageDao;
	}

	@Override
	public List<ProviderDocument> retrieveProviderDocuments(Long providerId, String providerType) {
		return providerDocumentRepository.findProviderDocuments(providerId, providerType);
	}

	@Override
	public void deleteAllProviderDocuments() throws Exception {
		List<ProviderDocument> providerDocumentList = providerDocumentDao.getAllProviderDocuments();
		for (ProviderDocument document : providerDocumentList) {
			fileStorageDao.deleteById(document.getFileId());
			providerDocumentDao.deleteProviderDocumentById(document.getId());
		}
	}

}
