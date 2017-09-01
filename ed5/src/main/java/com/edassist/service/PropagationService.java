package com.edassist.service;

import java.util.List;

import com.edassist.models.domain.Email;
import com.edassist.models.domain.FileContent;
import com.edassist.models.domain.General;

public interface PropagationService {

	void propagateGeneralContent(List<General> generalContentList);

	void deleteGeneralContent(General content);

	void propagateEmailContent(List<Email> emailContentList);

	void deleteEmailContent(Email content);

	General propagateFileContent(FileContent fileContent);

	void deleteContentByClient(String client);
}
