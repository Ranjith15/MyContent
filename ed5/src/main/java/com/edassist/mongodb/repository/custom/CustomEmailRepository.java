package com.edassist.mongodb.repository.custom;

import java.util.List;

public interface CustomEmailRepository {

	List<String> findUniqueComponents();

	List<String> findUniqueNamesForComponent(String component);
}
