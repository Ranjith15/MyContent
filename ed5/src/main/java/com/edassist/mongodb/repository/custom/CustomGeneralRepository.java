package com.edassist.mongodb.repository.custom;

import java.util.List;

public interface CustomGeneralRepository {

	List<String> findUniqueComponents();

	List<String> findUniqueNamesForComponent(String component);
	
	List<String> findCollectionNames();

}
