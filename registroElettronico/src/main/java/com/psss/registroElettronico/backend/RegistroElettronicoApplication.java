package com.psss.registroElettronico.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@SpringBootApplication
public class RegistroElettronicoApplication extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests(a -> a
						.mvcMatchers(GET, "/docenti").hasAuthority("READ")
						.anyRequest().hasAuthority("WRITE")
				).httpBasic();

		http.csrf().disable();
	}

	// In memory
//	@Bean
//	UserDetailsService userDetailsService() {
//		return new InMemoryUserDetailsManager
//				(User.withDefaultPasswordEncoder()
//						.username("fabio")
//						.password("fabio")
//						.roles("USER")
//						.build());
//	}

	// In database senza authorities
//	@Bean
//	UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource) {
//			@Override
//			protected List<GrantedAuthority> loadUserAuthorities(String username) {
//				return AuthorityUtils.createAuthorityList("ROLE_USER");
//			}
//		};
//	}

	// In database con authorities -> non serve se abbiamo la classe UserRepositoryUserService
//	@Bean
//	UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(RegistroElettronicoApplication.class, args);
	}

}
