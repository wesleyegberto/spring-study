package com.github.wesleyegberto.customsecurity.entity;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationToken implements Authentication {
	private static final long serialVersionUID = -1888594395489139462L;

	private boolean isAuthenticated;
	private UUID clientId;
	private String accountKey;
	private String apiKey;

	public AuthenticationToken(String accountKey, String apiKey) {
		this.accountKey = accountKey;
		this.apiKey = apiKey;
	}

	public AuthenticationToken(UUID clientId, String accountKey, String apiKey) {
		this(accountKey, apiKey);
		this.clientId = clientId;
	}

	@Override
	public Object getPrincipal() {
		return accountKey;
	}

	@Override
	public Object getCredentials() {
		return apiKey;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public Object getDetails() {
		return clientId;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}
