package com.github.wesleyegberto;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.mvcMatcher("/messages/**")
				.authorizeRequests()
					.mvcMatchers("/messages/**").access("hasAuthority('SCOPE_message.read')")
					.and()
				.oauth2ResourceServer()
					.jwt();
	}
}