package com.edassist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edassist.constants.MongoConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.Email;
import com.edassist.mongodb.repository.EmailRepository;
import com.edassist.service.EmailService;
import com.edassist.service.PropagationService;

@Component("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private PropagationService propagationService;

	@Override
	public List<Email> findByComponent(String component, String client, String program) {
		List<Email> emailList;
		if (program.isEmpty()) {
			emailList = emailRepository.findByComponentAndClient(component, client);
		} else {
			emailList = emailRepository.findByComponentAndClientAndProgram(component, client, program);
		}

		return emailList;
	}

	@Override
	public Email findByName(String component, String name, String client, String program, boolean cascade) throws BadRequestException {
		Email content = emailRepository.findByName(component, name, client, program);
		if (content == null && cascade) {
			content = emailRepository.findByName(component, name, client, MongoConstants.PROGRAM_CLIENT_DEFAULT);
			if (content == null) {
				content = emailRepository.findByName(component, name, MongoConstants.CLIENT_GLOBAL, "");
			}
		}

		return content;
	}

	@Override
	public List<Email> findByClient(String client) throws BadRequestException {
		List<Email> contentList = new ArrayList<>();
		contentList.addAll(emailRepository.findByClient(client));

		return contentList;
	}

	@Override
	public List<Email> findByProgram(String client, String program) {
		return emailRepository.findByClientAndProgram(client, program);
	}

	@Override
	public Email saveOrUpdate(Email email) {
		Email updatedEmail = new Email();
		// if we are updating an existing email record, preserve createdBy and createdAt values
		if (email.getId() != null) {
			Email oldEmail = emailRepository.findById(email.getId());
			if (oldEmail != null) {
				if (!email.getIsDeleted()) {
					email.setCreatedAt(oldEmail.getCreatedAt());
					email.setCreatedBy(oldEmail.getCreatedBy());
					email.setModifiedAt(new Date());
					email.setNeedsPropagation(true);
				}
			}
			updatedEmail = emailRepository.save(email);
		} else {
			if (!email.getIsDeleted()) {
				email.setCreatedAt(new Date());
				email.setNeedsPropagation(true);
				updatedEmail = emailRepository.save(email);
			}
		}
		return updatedEmail;
	}

	@Override
	public void deleteById(String id) {
		Email emailContent = emailRepository.findOne(id);
		if (emailContent != null) {
			propagationService.deleteEmailContent(emailContent);
		}
		emailRepository.delete(id);
	}

	@Override
	public List<String> findUniqueComponents() {
		return emailRepository.findUniqueComponents();

	}

	@Override
	public List<String> findUniqueNamesForComponent(String component) {
		return emailRepository.findUniqueNamesForComponent(component);
	}

}
