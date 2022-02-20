package com.github.wesleyegberto;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
	private RSAPublicKey key;

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.oauth2Login()
				.and()
			// Protected resources
			.authorizeRequests((authorizeRequests) ->
				authorizeRequests
					.antMatchers("/message/**").hasAuthority("SCOPE_message:read")
					.anyRequest().authenticated()
			)
			.oauth2ResourceServer((oauth2ResourceServer) ->
				oauth2ResourceServer
					.jwt((jwt) ->
						jwt.decoder(jwtDecoder())
					)
			);
	}

	@Bean
	JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(this.key).build();
	}
}