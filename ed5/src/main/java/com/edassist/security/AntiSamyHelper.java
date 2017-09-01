package com.edassist.security;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class AntiSamyHelper {
	private static Logger log = Logger.getLogger(AntiSamyHelper.class);

	public static String sanitizeXSS(String input) {
		String ret = input;
		try {
			if (aSamy == null) {
				log.error("AntiSamy is not initialized");
			} else if (input != null && input.trim().length() != 0) {
				ret = aSamy.scan(input, AntiSamy.SAX).getCleanHTML();
			}
		} catch (ScanException e) {
			log.error("Error in sanitizeXSS ", e);
		} catch (PolicyException e) {
			log.error("Error in sanitizeXSS ", e);
		}
		return ret;
	}

	public static String[] sanitizeXSS(String[] values) {
		if (aSamy == null) {
			log.error("AntiSamy is not initialized");
		} else if (values != null) {
			int length = values.length;
			String[] encodedValues = new String[length];
			for (int i = 0; i < length; i++) {
				encodedValues[i] = sanitizeXSS(values[i]);
			}
			return encodedValues;
		}
		return values;
	}

	public static void initialize(InputStream policyFile) throws PolicyException {
		asPolicy = Policy.getInstance(policyFile);
		aSamy = new AntiSamy(asPolicy);
	}

	public static Policy asPolicy;
	public static AntiSamy aSamy = null;
}
