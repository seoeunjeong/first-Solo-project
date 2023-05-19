package com.soloproject.community.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 인증 성공 후, 로그를 기록하거나
        log.info("# Authenticated successfully!");

        //사용자 정보를 response로 전송하는 등의 추가 작업을 할 수 있다.
        String username = authentication.getName();
        // 필요한 경우, 추가적인 사용자 정보를 가져올 수도 있습니다.

        // 출력 스트림 생성
        PrintWriter writer = response.getWriter();

        // response에 내용 작성
        writer.write("Welcome, " + username + "!");
        writer.flush();
        writer.close();
    }
}



