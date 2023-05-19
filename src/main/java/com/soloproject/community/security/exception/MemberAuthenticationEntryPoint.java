package com.soloproject.community.security.exception;

import com.soloproject.community.security.exception.ErrorResponder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class MemberAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Exception exception=(Exception)request.getAttribute("exception");
        ErrorResponder.sendErrorResponse(response, HttpStatus.UNAUTHORIZED);
        
        logExceptionMesssage(authException,exception);

    }
    private void logExceptionMesssage(AuthenticationException authException, Exception exception) {
        String massage =exception !=null? exception.getMessage():authException.getMessage();
        log.warn("Unauthorized  error happend: {}",massage);
    }
}
