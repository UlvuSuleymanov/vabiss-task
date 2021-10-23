package com.vabiss.task.security;


import com.vabiss.task.entity.User;
import com.vabiss.task.repository.UserRepository;
import com.vabiss.task.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtFilter extends OncePerRequestFilter {

     private final JwtService jwtService;
     private final UserRepository userRepository;

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader(AUTHORIZATION);
        if (requestHeader != null && requestHeader != "") {

            String token = requestHeader.replace("Bearer ", "");
            Long id = jwtService.getIdFromToken(token);
            User user = userRepository.getById(id);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    new UserPrincipal() {
                        @Override
                        public String getName() {
                            return null;
                        }
                    },
                    id,
                    Collections.emptyList()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);


        }

        filterChain.doFilter(request, response);
    }
}
