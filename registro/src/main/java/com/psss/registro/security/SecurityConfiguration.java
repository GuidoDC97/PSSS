package com.psss.registro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.http.HttpMethod.GET;

@EnableWebSecurity 
@Configuration 
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .requestCache().requestCache(new CustomRequestCache())
//                .and()
//                .authorizeRequests()
//                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
//                .anyRequest()
//                    .authenticated()
//                .and()
//                .formLogin()
//                    .loginPage(LOGIN_URL).permitAll()
//                    .loginProcessingUrl(LOGIN_PROCESSING_URL)
//                    .failureUrl(LOGIN_FAILURE_URL)
//                    .successHandler(myAuthenticationSuccessHandler())
//                .and()
//                .logout()
//                    .logoutSuccessUrl(LOGOUT_SUCCESS_URL);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and()
                .authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                .mvcMatchers("/segretario/**", "/").hasAuthority("SEGRETARIO")
                .mvcMatchers("/docente/**").hasAuthority("DOCENTE")
                .and()
                .formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .successHandler(myAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**",
                "/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}