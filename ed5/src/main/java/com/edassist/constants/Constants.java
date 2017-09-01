package com.edassist.constants;

public class Constants {

	public static final String REST_ISSUER_NAME = "edAssist.com";
	public static final int SESSION_TOKEN_LENGTH = 30 * 60000; // 30 minutes
	public static final int REFRESH_TOKEN_LENGTH = 8 * 30 * 60000; // 8 hours
	public static final String REFRESH_TOKEN_NAME = "refresh";
	public static final String SESSION_TOKEN_NAME = "session";
	public static final String OAUTH_END_POINT = "https://sso-dev.edassist.com/as/token.oauth2?";
	public static final String OAUTH_CLIENT_ID = "edassistMobileApp";
	// messages
	public static final String REST_RESET_PASSWORD_EMAIL = "Email has been sent to: ";
	public static final String REST_PASSWORD_CHANGED = "Password successfully changed";

	// error messages
	public static final String REST_MISSING_REQUIRED_FIELD = "Missing required fields.";
	public static final String BAD_REQUEST_MESSAGE = "Invalid Request.";
	public static final String INVALID_LOGIN_MESSAGE = "Invalid Login Information";
	public static final String NOT_FOUND_MESSAGE = "There is no resource behind the URI";
	public static final String CONTENT_NOT_FOUND_MESSAGE = "No Content found";
	public static final String REST_UNPROCESSABLE_ENTITY = "The server cannot process the entity";
	public static final String REST_UNAUTHORIZED = "Authentication credentials were missing or incorrect";
	public static final String REST_FORBIDDEN = "The server understood the request, but no permissions for that user";
	public static final String REST_MOBILE_FORBIDDEN = "Client is not mobile enabled";
	public static final String REST_INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String REST_EXCEEDED_MAX_RESULT = "Exceeded Max Results. Please modify search and try again";
	public static final String REST_UNEXPECTED_ERROR = "Unexpected error.";
	public static final String REST_NOT_CURRENT_USER = "User details does not match current user";
	public static final String REST_EDASSIST5_FORBIDDEN = "Client is not edassist5.0 enabled";
	public static final String CRM_ERROR_MESSAGE = "CRM Communication error";
	public static final String INVALID_APPLICATION_STATUS = "Invalid Application Status";
	public static final String INCOMPLETE_APPLICATION = "The application is incomplete. Please complete it before you submit";

	// Expensetype relation
	public static final String EXPENSETYPE_COURSE = "CRS";
	public static final String EXPENSETYPE_NON_COURSE = "NONCRS";
	public static final String EXPENSETYPE_BOTH = "BOTH";

	// Application Errors
	public static final String MAX_COURSES_EXCEEDED = "Maximum number of courses limit exceeded";
	
	// Application Contstants
	public static final String SESSION_TAMS_CLIENT_BRANDING = "TAMS_CLIENT_BRANDING";
	public static final String SESSION_TAMS_CLIENT_ID = "TAMS_CLIENT_ID";
	public static final String SESSION_TAMS_USER_ID = "TAMS_USER_ID";
	
	// Email Ids
	public static final long EMAIL_APP_APPROVAL_PARTICIPANT = 1;
	public static final long EMAIL_APP_APPROVAL_SUPERVISOR = 2;
	public static final long EMAIL_APP_DENIED_PARTICIPANT = 3;
	public static final long EMAIL_APP_DENIED_SUPERVISOR = 4;
	public static final long EMAIL_APPEAL_PENDING_PARTICIPANT = 5;
	public static final long EMAIL_APPEAL_STATUS_UPDATE_PARTICIPANT = 6;
	public static final long EMAIL_PAYMENT_INCOMPLETE_PARTICIPANT = 7;
	public static final long EMAIL_PAYMENT_PROCESSED_PARTICIPANT = 8;
	public static final long EMAIL_LOC_PARTICIPANT = 9;
	public static final long EMAIL_LOC_SUPERVISOR = 10;
	public static final long EMAIL_REQUIRES_SUPERVISOR1_APPROVAL = 11;
	public static final long EMAIL_DOCUMENT_RECEIVED_PARTICIPANT = 12;
	public static final long EMAIL_COURSE_COMPLETION_1 = 13;
	public static final long EMAIL_COURSE_COMPLETION_2 = 14;
	public static final long EMAIL_COURSE_COMPLETION_3 = 15;
	public static final long EMAIL_COURSE_COMPLETION_4 = 18;
	public static final long EMAIL_COURSE_COMPLETION_5 = 19;
	public static final long EMAIL_REQUIRES_SUPERVISOR2_APPROVAL = 17;
	public static final long EMAIL_APPLICATION_SUBMISSION_INCOMPLETE = 20;
	public static final long EMAIL_APP_CARRYOVER_PAYMENT_APPROVED = 21;
	public static final long EMAIL_SAVED_NOT_SUBMITTED = 28;
	public static final long EMAIL_SENT_TO_COLLECTIONS = 29;
	public static final long EMAIL_APPLICATION_CLSOED = 30;
	public static final long EMAIL_APPLICATION_RETURN = 32;
	public static final long EMAIL_APPLICATION_SUBMISSION_MANUAL = 33;
	public static final long EMAIL_IDC_GRADES_NONCOMPLIENT = 35;
}
