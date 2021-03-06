package com.pazarfy.ws.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pazarfy.ws.auth.AuthTokenFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint());

		http.headers().frameOptions().disable();

		http
				.authorizeRequests()
				//.antMatchers(HttpMethod.GET, "/api/1.0/users").authenticated()
				.antMatchers(HttpMethod.PUT, "/api/1.0/users/{username}").authenticated()
				.antMatchers(HttpMethod.POST, "/api/1.0/feeds").authenticated()
				.antMatchers(HttpMethod.POST, "/api/1.0/attachments").authenticated()
				.and()
				.authorizeRequests().anyRequest().permitAll();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthTokenFilter authTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}

// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

// @Autowired
// UserAuthService userAuthService;

// @Override
// protected void configure(HttpSecurity http) throws Exception {

// http.csrf().disable();
// http.headers().frameOptions().disable();
// http.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint());

// http.authorizeRequests()
// // .antMatchers(HttpMethod.POST, "/api/1.0/auth").authenticated()
// .antMatchers(HttpMethod.PUT, "/api/1.0/users/{username}").authenticated()
// .antMatchers(HttpMethod.POST, "/api/1.0/feeds").authenticated()
// .antMatchers(HttpMethod.POST, "/api/1.0/attachments").authenticated()
// .and().authorizeRequests().anyRequest().permitAll();

// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
// http.addFilterBefore(tokenFilter(),
// UsernamePasswordAuthenticationFilter.class);
// }

// // @Override
// // protected void configure(AuthenticationManagerBuilder auth) throws
// Exception {
// //
// auth.userDetailsService(userAuthService).passwordEncoder(passwordEncoder());
// // }

// @Bean
// PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
// }

// @Bean
// AuthTokenFilter tokenFilter() {
// return new AuthTokenFilter();
// }

// }