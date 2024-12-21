package com.heychic.store.config;

import com.heychic.store.service.impl.UserSecurityService;
import com.heychic.store.form.RoleCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import utility.SecurityUtility;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/",
			"/new-user",
			"/login",
			"/store",
			"/product-detail"
	};

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers(PUBLIC_MATCHERS).permitAll() // Public endpoints
				.antMatchers("/admin/**").hasAnyRole("ADMIN", "EMPLOYEE") // Admin or Employee-only endpoints
				.anyRequest().authenticated() // All other requests require authentication
				.and()
				.csrf().disable().cors().disable()
				.formLogin()
				.failureUrl("/login?error")
				.loginPage("/login")
				.successHandler(customAuthenticationSuccessHandler()) // Explicitly use custom handler
				.permitAll()
				.and()
				.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/?logout")
				.deleteCookies("remember-me")
				.permitAll()
				.and()
				.addFilterBefore(roleCheckFilter(), UsernamePasswordAuthenticationFilter.class)
				.rememberMe()
				.key("aSecretKey");
	}


	@Bean
	public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return (request, response, authentication) -> {
			// Redirect based on role
			String redirectUrl = "/";
			if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
				redirectUrl = "/admin"; // Admins go to /admin
			} else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"))) {
				redirectUrl = "/"; // Employees go to storefront home page /
			} else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
				redirectUrl = "/"; // Users go to storefront home page /
			}
			response.sendRedirect(redirectUrl);
		};
	}

	@Bean
	public RoleCheckFilter roleCheckFilter() {
		return new RoleCheckFilter();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}
