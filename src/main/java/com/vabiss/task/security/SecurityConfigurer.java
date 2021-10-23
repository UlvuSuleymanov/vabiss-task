package com.vabiss.task.security;

import com.vabiss.task.exception.handler.AuthenticationExceptionHandler;
import com.vabiss.task.repository.UserRepository;
import com.vabiss.task.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final AuthenticationExceptionHandler authenticationExceptionHandler;

    @Autowired
    public SecurityConfigurer(JwtService jwtService,
                              UserRepository userRepository,
                              UserDetailsService userDetailsService, AuthenticationExceptionHandler authenticationExceptionHandler) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.authenticationExceptionHandler = authenticationExceptionHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationExceptionHandler)
                .and()
                .authorizeRequests()
                .antMatchers(POST, "/api/account/signup").permitAll()
                .antMatchers(POST, "/api/authorize").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwtService, userRepository), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();

    }

}
