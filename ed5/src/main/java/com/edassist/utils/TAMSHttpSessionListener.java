package com.edassist.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;
import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.edassist.constants.Constants;

@Component
public class TAMSHttpSessionListener implements javax.servlet.http.HttpSessionListener {

	private static Logger log = Logger.getLogger(TAMSHttpSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("deanhere - in TAMSHttpSessionListener.sessionCreated(...)");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("deanhere - in TAMSHttpSessionListener.sessionDestroyed(...)");

		String userIdStr = (String) event.getSession().getAttribute(Constants.SESSION_TAMS_USER_ID);
		String serverName = CommonUtil.getServerName();
		if (userIdStr != null) {
			javax.naming.Context context = null;
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			try {
				context = new InitialContext();
				javax.sql.DataSource ds = (javax.sql.DataSource) context.lookup("java:comp/env/tamsDB");
				connection = ds.getConnection();
				preparedStatement = connection.prepareCall("{call CreateWorkflow_ProcessProcessorLogout(?,?)}");
				preparedStatement.setInt(1, new Long(userIdStr).intValue());
				preparedStatement.setString(2, event.getSession().getId());
				preparedStatement.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null) {
						preparedStatement.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}