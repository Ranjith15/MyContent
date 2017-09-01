package com.edassist.service.impl;

import com.edassist.constants.MongoConstants;
import com.edassist.exception.BadRequestException;
import com.edassist.models.domain.Email;
import com.edassist.models.domain.FileContent;
import com.edassist.models.domain.General;
import com.edassist.mongodb.repository.EmailRepository;
import com.edassist.mongodb.repository.GeneralRepository;
import com.edassist.service.FileService;
import com.edassist.service.PropagationService;
import com.edassist.utils.CommonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class PropagationServiceImpl implements PropagationService {

	private static Logger log = Logger.getLogger(PropagationServiceImpl.class);

	@Autowired
	private GeneralRepository generalRepository;

	@Autowired
	private EmailRepository emailRepository;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public void propagateGeneralContent(List<General> generalContentList) {
		log.debug("Propagating General Content Started");
		generalContentList.forEach(generalContent -> {
			log.debug("Propagating General Content: " + generalContent.getName() + " for Client: " + generalContent.getClient());
			if (generalContent.getNeedsPropagation() && !generalContent.getIsDeleted()) {
				saveGeneralContent(generalContent);
			}
			if (!generalContent.getName().equals(MongoConstants.NAME_CLIENT_DOCUMENT)) {
				generalContent.setNeedsPropagation(false);
				generalContent.setPublishedDate(new Date());
				generalRepository.save(generalContent);
			}
		});
		log.debug("Propagating General Content Ended");
	}

	@Override
	public void deleteGeneralContent(General generalContent) {
		generalContent.setIsDeleted(true); //Setting it to enable deletion in the propagated server
		saveGeneralContent(generalContent);
	}

	@Override
	public void propagateEmailContent(List<Email> emailContentList) {
		log.debug("Propagating Email Content Started");
		emailContentList.forEach(emailContent -> {
			log.debug("Propagating Email Content: " + emailContent.getName() + " for Client: " + emailContent.getClient());
			if (emailContent.getNeedsPropagation() && !emailContent.getIsDeleted()) {
				saveEmailContent(emailContent);
			}
			emailContent.setNeedsPropagation(false);
			emailContent.setPublishedDate(new Date());
			emailRepository.save(emailContent);
		});
		log.debug("Propagating Email Content Ended");
	}

	@Override
	public void deleteEmailContent(Email emailContent) {
		emailContent.setIsDeleted(true);
		saveEmailContent(emailContent);
	}

	@Override
	public General propagateFileContent(FileContent fileContent) {
		General generalContent = generalRepository.findOne(fileContent.getGeneralContentId());
		if (generalContent.getNeedsPropagation() && !generalContent.getIsDeleted()) {
			saveFileContent(fileContent);
		}
		return generalContent;
	}

	@Override
	public void deleteContentByClient(String client) {
		String url = getPropagationUrl();
		if (!StringUtils.isEmpty(url)) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content-propagation").pathSegment("remove-client-content").queryParam("clientId", client);
			restTemplate.delete(builder.build().encode().toUri());
		}
	}

	private String getPropagationUrl() {
		return CommonUtil.getServiceHost(CommonUtil.CONTENT_PROPAGATION_SERVICE_HOST, "ed5");
	}

	private void saveGeneralContent(General generalContent) {
		String url = getPropagationUrl();
		if (!StringUtils.isEmpty(url)) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("general").pathSegment(generalContent.getId());
			restTemplate.put(builder.build().encode().toUri(), generalContent);
		}
	}

	private void saveEmailContent(Email emailContent) {
		String url = getPropagationUrl();
		if (!StringUtils.isEmpty(url)) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("email").pathSegment(emailContent.getId());
			restTemplate.put(builder.build().encode().toUri(), emailContent);
		}
	}

	private Boolean saveFileContent(FileContent fileContent) {
		String url = getPropagationUrl();
		if (!StringUtils.isEmpty(url)) {
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("file");
			return restTemplate.postForObject(builder.build().encode().toUri(), fileContent, Boolean.class);
		}
		return null;
	}
}
