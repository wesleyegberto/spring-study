package com.github.wesleyegberto.customsecurity.security;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.wesleyegberto.customsecurity.entity.AuthenticationToken;

@Service
public class KeysAuthenticator {
	public AuthenticationToken authenticate(String accountKey, String apiKey) {
		System.out.println("Authenticating " + accountKey + " - " + apiKey);
		
		if (!"xxx".equals(accountKey) || !"yyy".equals(apiKey))
			return null;
		
		UUID clientId = UUID.randomUUID();
		return new AuthenticationToken(clientId, accountKey, apiKey);
	}
}
