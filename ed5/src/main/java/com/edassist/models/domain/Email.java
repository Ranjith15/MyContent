package com.edassist.models.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "email")
public class Email {

	@Id
	private String id;
	private String type;
	private String component;
	private String locale;
	private String client;
	private String program;
	private String name;
	private String subject;
	private String ccEmailId;
	private String bccEmailId;
	private String body;
	private Long createdBy;
	private Date createdAt;
	private Long modifiedBy;
	private Date modifiedAt;
	private boolean needsPropagation;
	private boolean isDeleted;
	private Date publishedDate;
	private Long publishedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCcEmailId() {
		return ccEmailId;
	}

	public void setCcEmailId(String ccEmailId) {
		this.ccEmailId = ccEmailId;
	}

	public String getBccEmailId() {
		return bccEmailId;
	}

	public void setBccEmailId(String bccEmailId) {
		this.bccEmailId = bccEmailId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public boolean getNeedsPropagation() {
		return needsPropagation;
	}

	public void setNeedsPropagation(boolean needsPropagation) {
		this.needsPropagation = needsPropagation;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Long getPublishedBy() {
		return publishedBy;
	}

	public void setPublishedBy(Long publishedBy) {
		this.publishedBy = publishedBy;
	}
}
