package com.github.wesleyegberto.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * For configuring the end users recognized by this Authorization Server
 */
@EnableWebSecurity
@Order(1)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.mvcMatchers("/.well-known/jwks.json").permitAll()
				.anyRequest().authenticated()
				.and()
			// We can choose the way to login
			.httpBasic() // in case of implicit flow
			// .formLogin()
				.and()
			.csrf()
				.ignoringRequestMatchers((request) -> "/introspect".equals(request.getRequestURI()));
		// @formatter:on
	}

	@Bean
	public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) throws Exception {
		// @formatter:off
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("admin")
			.password(passwordEncoder.encode("password"))
			.roles("USER", "ADMIN")
			.build());
		manager.createUser(User.withUsername("user")
			.password(passwordEncoder.encode("password"))
			.roles("USER")
			.build());
		// @formatter:on
		return manager;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}