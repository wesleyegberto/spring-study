package com.github.wesleyegberto.customsecurity.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.wesleyegberto.customsecurity.entity.AuthenticationToken;

public class KeyAuthenticationFilter extends OncePerRequestFilter {
	private static final String API_KEY_HEADER = "x-api-key";
	private static final String ACCOUNT_KEY_HEADER = "x-accountmanager-key";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String accountKey = request.getHeader(ACCOUNT_KEY_HEADER);
		String apiKey = request.getHeader(API_KEY_HEADER);
		// String userEmail = request.getHeader("x-user-email");

		if (!isValidKeys(accountKey, apiKey)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key or Account Manager Key");
			return;
		}

		// create the authentication and set it on request
		Authentication auth = new AuthenticationToken(accountKey, apiKey);
		SecurityContextHolder.getContext().setAuthentication(auth);

		filterChain.doFilter(request, response);
	}

	private boolean isValidKeys(String accountKey, String apiKey) {
		return accountKey != null && apiKey != null;
	}
}