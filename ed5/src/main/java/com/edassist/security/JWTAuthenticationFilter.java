package com.edassist.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.edassist.exception.UnauthorizedException;

public class JWTAuthenticationFilter extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnauthorizedException, Exception {
		return true;
	}

}
