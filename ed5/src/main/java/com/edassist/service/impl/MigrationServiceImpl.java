package com.edassist.service.impl;

import com.edassist.constants.MongoConstants;
import com.edassist.models.contract.MigratedContent;
import com.edassist.models.domain.Email;
import com.edassist.models.domain.General;
import com.edassist.service.EmailService;
import com.edassist.service.GeneralService;
import com.edassist.service.MigrationService;
import com.edassist.utils.CommonUtil;
import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class MigrationServiceImpl implements MigrationService {

	private RestTemplate restTemplate = new RestTemplate();

	private final EmailService emailService;
	private final GeneralService generalService;

	@Autowired
	public MigrationServiceImpl(EmailService emailService, GeneralService generalService) {
		this.emailService = emailService;
		this.generalService = generalService;
	}

	@Override
	public void migrateClient(String client) throws Exception {
		List<MigratedContent> migratedContents = retrieveContentFromTams4(client);

		for (MigratedContent migratedContent : migratedContents) {
			if (filterOnly5NeededContent(migratedContent)) {
				mapContentToMongo(migratedContent, client);
			}
		}
	}

	private boolean filterOnly5NeededContent(MigratedContent migratedContent) {
		if (migratedContent.getContentCategoryProp().equals("ApplicationStatus")) {
			return true;
		}
		if (migratedContent.getContentCategoryProp().equals("AppReviewAction")) {
			return true;
		}
		if (migratedContent.getContentCategoryProp().equals("ApplicationStep3") && !migratedContent.getContentNameProp().equals("Step3Intro")) {
			return true;
		}
		if (migratedContent.getContentCategoryProp().equals("CompanyAppReview") && !migratedContent.getContentNameProp().equals("ReviewIntro")) {
			return true;
		}
		List<String> contentNamesFor5 = new ArrayList<>(
				Arrays.asList("Code", "ApplCourseTax", "CourseQuestionOne", "CourseQuestionTwo", "CourseQuestionThree", "ApplExpenseTax", "ExpenseQuestionOne", "ExpenseQuestionTwo",
						"ExpenseQuestionThree", "AppealStatusUpdateEmailParticipant", "ApplicationApprovalEmailParticipant", "ApplicationApprovalEmailSupervisor", "ApplicationDeniedEmailParticipant",
						"ApplicationDeniedEmailSupervisor", "ApplIncompleteEmailParticipant", "ChangePasswordIntro", "CltFullSvcAdminMessage", "CltAppealDesigneeMessage",
						"CltParticipantMessage", "MyProgResources", "CltSupervisorMessage", "ContactUs", "CourseCompletion1", "CourseCompletion2", "CourseCompletion3", "CourseCompletion4",
						"CourseCompletion5", "DocumentReceivedEmailParticipant", "ApplEducationProvSearch", "GraduationQuestion", "LOCEmailParticipant", "LOCEmailSupervisor", "LoginInstruction",
						"LoginPasswordField", "LoginUsernameField", "MyPersonalProfPageIntro", "ExpenseNonTaxablePopUpInfo", "PaymentIncompleteEmailParticipant", "PaymentProcessedEmailParticipant",
						"PrivacyPolicy", "PasswordResetEmail", "ApplSessionInfo", "ExpenseTaxablePopUpInfo", "CourseNonTaxablePopUpInfo", "CourseTaxablePopUpInfo", "Instructions",
						"LOCTemplate", "SystemCancellation", "ProviderGuideLines", "CarryoverPaymentApproved", "AppealStatusUpdate", "AppealRequiresReview", "SavedNotSubmitted", "AppReturnComment",
						"Incomplete", "Approved", "Denied", "Cancelled", "PageIntro", "IneligibleProvider", "ApplicationClosed", "SessionInvalidDates", "ClientLogo", "SentToCollections",
						"AppManualEmailParticipant", "RequiresApprovalEmailSupervisor1", "RequiresApprovalEmailSupervisor2", "SubmittedPendingReview", "ForwardedtoApprover", "TermsAndConditions"));
		return contentNamesFor5.contains(migratedContent.getContentNameProp());
	}

	private void mapContentToMongo(MigratedContent migratedContent, String client) throws Exception {
		if (migratedContent.getContentTypeProp().equals("Emails")) {
			Email emailContent = new Email();

			emailContent.setType("email");
			emailContent.setComponent(determineComponent(migratedContent.getContentCategoryProp()));
			emailContent.setLocale("en");
			emailContent.setClient(client);
			emailContent.setProgram(firstLetterLowerCase(migratedContent.getProgram()));
			emailContent.setName(firstLetterLowerCase(migratedContent.getContentNameProp()));
			emailContent.setSubject(migratedContent.getDescription());
			emailContent.setCcEmailId(null);
			emailContent.setBccEmailId(migratedContent.getBccEmail());
			emailContent.setBody(migratedContent.getArtifactString());
			emailContent.setCreatedBy(MongoConstants.DEFAULT_USER_ID);
			emailContent.setCreatedAt(migratedContent.getCreatedDate().getTime());
			emailContent.setModifiedBy(MongoConstants.DEFAULT_USER_ID);
			emailContent.setModifiedAt(new Date());
			emailContent.setNeedsPropagation(true);

			emailService.saveOrUpdate(emailContent);
		} else {
			General generalContent = new General();

			generalContent.setComponent(determineComponent(migratedContent.getContentCategoryProp()));
			generalContent.setLocale("en");
			generalContent.setClient(client);
			generalContent.setProgram(firstLetterLowerCase(migratedContent.getProgram()));
			generalContent.setName(firstLetterLowerCase(migratedContent.getContentNameProp()));
			generalContent.setFileId(null);
			generalContent.setFileName(null);
			generalContent.setFilePath(null);
			generalContent.setData(migratedContent.getArtifactString());
			generalContent.setType("general");
			generalContent.setTitle("");
			generalContent.setCreatedBy(MongoConstants.DEFAULT_USER_ID);
			generalContent.setCreatedAt(migratedContent.getCreatedDate().getTime());
			generalContent.setModifiedBy(MongoConstants.DEFAULT_USER_ID);
			generalContent.setModifiedAt(new Date());
			generalContent.setStartDate(new Date(migratedContent.getStartDate()));
			generalContent.setEndDate(new Date(migratedContent.getEndDate()));
			generalContent.setNeedsPropagation(true);

			generalService.saveOrUpdate(generalContent);
		}
	}

	private String determineComponent(String oldComponent) {
		String newComponent;

		switch (oldComponent) {
		case "ApplicationStep1":
			newComponent = "applicationStep2";
			break;
		case "ApplicationStep2":
			newComponent = "applicationStep3";
			break;
		case "ApplicationStep3":
			newComponent = "applicationStep4";
			break;
		default:
			newComponent = firstLetterLowerCase(oldComponent);
			break;
		}

		return newComponent;
	}

	private List<MigratedContent> retrieveContentFromTams4(String client) {
		String url = CommonUtil.getServiceHost(CommonUtil.CONTENT_MIGRATION_SERVICE_HOST, "TAMS4Web");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).pathSegment("content").pathSegment("find-all-content-by-client").pathSegment(client);
		MigratedContent[] result = restTemplate.getForObject(builder.build().encode().toUri(), MigratedContent[].class);
		return new ArrayList<>(Arrays.asList(result));
	}

	private static String firstLetterLowerCase(String inputString) {
		if (inputString.equals("")) {
			return inputString;
		}
		char c[] = inputString.toCharArray();
		c[0] = Character.toLowerCase(c[0]);
		inputString = new String(c);

		return inputString;
	}
}
