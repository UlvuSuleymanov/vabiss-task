package com.vabiss.task.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vabiss.task.exception.SimpleErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class AuthenticationExceptionHandler implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        SimpleErrorResponse simpleError = new SimpleErrorResponse();
        simpleError.setTitle("Giriş qadağandır");
        simpleError.setDetail("Bu səhifəyə giriş üçün token əldə etməlisiniz");
        ObjectMapper mapper = new ObjectMapper();
        String responseMsg = mapper.writeValueAsString(simpleError);
        httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(responseMsg);

    }
}
