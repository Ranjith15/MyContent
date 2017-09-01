package com.edassist.utils;

import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TAMSSpringHttpSessionListener implements javax.servlet.http.HttpSessionListener, ApplicationContextAware {

	private static Logger log = Logger.getLogger(TAMSSpringHttpSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("deanhere - in TAMSSpringHttpSessionListener.sessionCreated(...)");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("deanhere - in TAMSSpringHttpSessionListener.sessionDestroyed(...)");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("deanhere - in TAMSSpringHttpSessionListener.setApplicationContext(...)");

		/*
		 * The ServletConfig implementation in our EdAssist 4.0 configuration is too old to support the "addListener(..)" method. So, while this setApplicationContext(...) method can be driven at
		 * startup, we are unable to establish a Spring context in which to register listeners or do any Spring work. Damn! Use the non-Spring TAMSHttpSessionListener class instead.
		 */

		/*
		 * try { if (applicationContext instanceof WebApplicationContext) { ((ServletContext)((WebApplicationContext)applicationContext).getServletContext()).addListener(this); } } catch (Exception e)
		 * { // Spring is unable to locate "addListener(...) method. Please see note above. e.printStackTrace(); }
		 */
	}
}