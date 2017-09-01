package com.edassist.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.edassist.exception.BadRequestException;

public class CommonUtil {

	private static Logger log = Logger.getLogger(CommonUtil.class);

	// service hosts
	public static final String CONTENT_MIGRATION_SERVICE_HOST = "services.hosts.migration.content";
	public static final String CONTENT_PROPAGATION_SERVICE_HOST = "services.hosts.propagation.content";

	// SWAGGER Documentation
	public static final String SWAGGER_DOCUMENTATION = "swagger.documentation.enabled";

	public static Properties hotProperties;

	public static void loadHotProperties() throws Exception {
		File configFile = new File("C:\\TAMSLocalConfig\\TAMS4_HOT_CONFIGURATION.properties");
		hotProperties = new Properties();
		InputStream is = null;
		is = new FileInputStream(configFile);
		hotProperties.load(is);
	}

	public static String getHotProperty(String propertyKey) {
		String returnValue = "";
		try {
			returnValue = (hotProperties.getProperty(propertyKey) == null ? "" : hotProperties.getProperty(propertyKey));
		} catch (Exception e) {
			throw new BadRequestException("Not able to get hot property value");
		}
		return returnValue;
	}

	public static String getServerName() {
		Map<String, String> env = System.getenv();
		if (env == null) {
			return "unknown";
		}
		String serverName = env.get("SERVER_NAME");
		if (serverName == null || serverName.length() < 1) {
			return "unknown";
		}

		return serverName;
	}

	public static String getServiceHost(String host, String env) {
		String hostFromProperty = getHotProperty(host);
		if (hostFromProperty == null || hostFromProperty.isEmpty()) {
			return null;
		}
		return "http://" + hostFromProperty + "/" + env + "/api/v1/";
	}
}
