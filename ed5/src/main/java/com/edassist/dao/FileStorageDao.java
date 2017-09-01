package com.edassist.dao;

import java.io.InputStream;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public interface FileStorageDao {

	public GridFSDBFile getById(String id);

	public GridFSFile saveFile(InputStream inputStream, String fileName);

	public GridFSDBFile getByName(String fileName);

	public void deleteById(String id);

}
