package com.edassist.dao.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.edassist.dao.FileStorageDao;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Repository
public class FileStorageDaoImpl implements FileStorageDao {

	@Autowired
	private GridFsOperations gridFsTemplate;

	@Override
	public GridFSDBFile getById(String id) {
		return gridFsTemplate.findOne(new Query().addCriteria((Criteria.where("_id").is(id))));
	}

	@Override
	public GridFSDBFile getByName(String fileName) {
		return gridFsTemplate.findOne(new Query().addCriteria((Criteria.where("filename").is(fileName))));
	}

	@Override
	public GridFSFile saveFile(InputStream inputStream, String fileName) {
		GridFSFile savedFile = gridFsTemplate.store(inputStream, fileName);
		return savedFile;
	}

	@Override
	public void deleteById(String id) {
		gridFsTemplate.delete(new Query().addCriteria((Criteria.where("_id").is(id))));
	}

}
