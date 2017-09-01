package com.edassist.models.domain;

public class FileContent {

	private String generalContentId;
	private String fileName;
	private byte[] file;
	private Long publishedBy;

	public String getGeneralContentId() {
		return generalContentId;
	}

	public void setGeneralContentId(String generalContentId) {
		this.generalContentId = generalContentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public Long getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(Long publishedBy) {
		this.publishedBy = publishedBy;
	}
}
