package com.edassist.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edassist.models.domain.Email;
import com.edassist.models.domain.FileContent;
import com.edassist.mongodb.repository.EmailRepository;
import com.edassist.mongodb.repository.GeneralRepository;
import com.edassist.service.EmailService;
import com.edassist.service.PropagationService;
import com.edassist.service.GeneralService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edassist.models.domain.General;

@RestController
public class PropagationController {

	private static Logger log = Logger.getLogger(PropagationController.class);

	private final PropagationService propagationService;
	private final GeneralRepository generalRepository;
	private final EmailRepository emailRepository;
	private final GeneralService generalService;
	private final EmailService emailService;

	@Autowired
	public PropagationController(PropagationService propagationService, GeneralRepository generalRepository, EmailRepository emailRepository, GeneralService generalService, EmailService emailService) {
		this.generalService = generalService;
		this.emailService = emailService;
		this.propagationService = propagationService;
		this.generalRepository = generalRepository;
		this.emailRepository = emailRepository;
	}

	@RequestMapping(value = "/v1/content/general/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateGeneralContent(@RequestBody General generalContent) {
		if (generalContent != null) {
			List<General> generalContentList = new ArrayList();
			generalContentList.add(generalContent);
			propagationService.propagateGeneralContent(generalContentList); //Move this to the propagation server
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/email/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateGeneralContent(@RequestBody Email emailContent) {
		if (emailContent != null) {
			List<Email> emailContentList = new ArrayList();
			emailContentList.add(emailContent);
			propagationService.propagateEmailContent(emailContentList); //Move this to the propagation server
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/file/propagate-content", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateGeneralContent(@RequestBody FileContent fileContent) {
		log.debug("Propagating File Content: " + fileContent.getFileName());
		General generalContent = propagationService.propagateFileContent(fileContent); //Move this to the propagation server
		log.debug("Propagated File Content" + generalContent.getName());
		generalContent.setNeedsPropagation(false);
		generalContent.setPublishedDate(new Date());
		generalContent.setPublishedBy(fileContent.getPublishedBy());
		generalRepository.save(generalContent);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content-propagation/propagate-client", method = RequestMethod.POST)
	public ResponseEntity<Void> propagateClient(@RequestParam String clientId) throws Exception {
		List<General> generalContentList = generalRepository.findByClient(clientId);
		propagationService.propagateGeneralContent(generalContentList);

		List<Email> emailContentList = emailRepository.findByClient(clientId);
		propagationService.propagateEmailContent(emailContentList);

		//Delete ready to remove (isDeleted: true) content from the propagation server
		propagationService.deleteContentByClient(clientId);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content-propagation/remove-client-content", method = RequestMethod.DELETE)
	public ResponseEntity<Void> removeContentByClient(@RequestParam String clientId) throws Exception {
		log.debug("Start of Delete General Content (/remove-client-content)");
		List<General> generalContentList = generalRepository.findByClientAndIsDeletedTrue(clientId);
		generalContentList.forEach(generalContent -> {
			log.debug("Client: " + generalContent.getClient() + "Content Name: "+ generalContent.getName());
			if (generalContent.getIsDeleted()) {
				generalService.deleteById(generalContent.getId());
			}
		});
		log.debug("End of Delete General Content(/remove-client-content)");
		log.debug("Start of Delete Email Content(/remove-client-content)");
		List<Email> emailContentList = emailRepository.findByClientAndIsDeletedTrue(clientId);
		emailContentList.forEach(emailContent -> {
			log.debug("Client: " + emailContent.getClient() + "Content Name: "+ emailContent.getName());
			if (emailContent.getIsDeleted()) {
				emailService.deleteById(emailContent.getId());
			}
		});
		log.debug("End of Delete Email Content(/remove-client-content)");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/v1/content/clients/{clientId}/propagation-client-content-size", method = RequestMethod.GET)
	public Long getNeedsPropagationContentSizeByClient(@PathVariable(value = "clientId") String clientId) throws Exception {
		List<General> generalContentList = generalRepository.findByClientAndNeedsPropagationTrue(clientId);
		List<Email> emailContentList = emailRepository.findByClientAndNeedsPropagationTrue(clientId);
		return new Long(generalContentList.size()+emailContentList.size());
	}
}
