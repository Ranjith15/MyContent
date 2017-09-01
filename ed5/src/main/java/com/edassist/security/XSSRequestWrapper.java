package com.edassist.security;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
	private static Logger log = Logger.getLogger(XSSRequestWrapper.class);

	public XSSRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
		this.servletRequest = servletRequest;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServletRequestWrapper#getHeader(java.lang.String)
	 */
	@Override
	public String getHeader(String name) {
		String header = super.getHeader(name);
		return AntiSamyHelper.sanitizeXSS(header);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
	 */
	@Override
	public String getParameter(String name) {
		String param = super.getParameter(name);
		String ret = AntiSamyHelper.sanitizeXSS(param);
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
	 */
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values != null) {
			int length = values.length;
			String[] encodedValues = new String[length];
			for (int i = 0; i < length; i++) {
				encodedValues[i] = AntiSamyHelper.sanitizeXSS(values[i]);
			}
			return encodedValues;
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServletRequestWrapper#getQueryString()
	 */
	@Override
	public String getQueryString() {
		String queryString = super.getQueryString();
		if (queryString != null && queryString.trim().length() > 0) {
			List<NameValuePair> paramList = URLEncodedUtils.parse(queryString, Charset.forName("UTF-8"));
			List<NameValuePair> encodedParamList = new ArrayList<NameValuePair>();
			if (paramList != null && paramList.size() > 0) {
				for (NameValuePair pair : paramList) {
					encodedParamList.add(new BasicNameValuePair(pair.getName(), AntiSamyHelper.sanitizeXSS(pair.getValue())));
				}
				return URLEncodedUtils.format(encodedParamList, "UTF-8");
			}
		}
		return super.getQueryString();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return super.getInputStream();
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> paramMap = new HashMap<String, String[]>();
		if (servletRequest instanceof DefaultMultipartHttpServletRequest) {
			paramMap.putAll(((DefaultMultipartHttpServletRequest) servletRequest).getParameterMap());
		} else {
			paramMap = super.getParameterMap();
		}
		if (paramMap != null && paramMap.size() > 0) {
			Map<String, String[]> encodedMap = new HashMap<String, String[]>();
			for (String key : paramMap.keySet()) {
				encodedMap.put(key, AntiSamyHelper.sanitizeXSS(paramMap.get(key)));
			}
			return Collections.unmodifiableMap(encodedMap);
		}

		return super.getParameterMap();
	}

	private HttpServletRequest servletRequest;
}
