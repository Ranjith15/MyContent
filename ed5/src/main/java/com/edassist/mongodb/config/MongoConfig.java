package com.edassist.mongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.edassist.mongodb.repository")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "edAssistContent";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("coaeda5cntstg02");
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.edassist.mongodb";
	}
}