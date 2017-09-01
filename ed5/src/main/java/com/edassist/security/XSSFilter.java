package com.edassist.security;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class XSSFilter implements Filter {
	private static Logger log = Logger.getLogger(XSSFilter.class);
	private String mode = "DENY";

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		chain.doFilter(new XSSRequestWrapper(req), response);
		HttpServletResponse res = (HttpServletResponse) response;
		res.addHeader("X-FRAME-OPTIONS", mode);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String configMode = filterConfig.getInitParameter("mode");
		if (configMode != null) {
			mode = configMode;
		}
		try {
			InputStream policyFile = filterConfig.getServletContext().getResourceAsStream("/WEB-INF/antisamy.xml");
			AntiSamyHelper.initialize(policyFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Error in Filter ", e);
		}

	}

}
