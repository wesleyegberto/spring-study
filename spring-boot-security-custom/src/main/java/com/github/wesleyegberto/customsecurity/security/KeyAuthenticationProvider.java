package com.github.wesleyegberto.customsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.github.wesleyegberto.customsecurity.entity.AuthenticationToken;

@Component
public class KeyAuthenticationProvider implements AuthenticationProvider {
	private final KeysAuthenticator authenticator;

	@Autowired
	public KeyAuthenticationProvider(KeysAuthenticator userRepository) {
		this.authenticator = userRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationToken authenticationToken = (AuthenticationToken) authentication;
		
		Authentication authenticatedUser = authenticator.authenticate(authenticationToken.getPrincipal().toString(), authentication.getCredentials().toString());
		if (authenticatedUser == null) {
			throw new BadCredentialsException("Invalid Account Manager Key or API Key");
		}
		return authenticatedUser;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AuthenticationToken.class.isAssignableFrom(authentication);
	}

}