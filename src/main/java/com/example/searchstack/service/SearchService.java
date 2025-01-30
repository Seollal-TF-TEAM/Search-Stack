package com.example.searchstack.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
@Service
public class SearchService {

    public void search(String query) {
        log.debug("Search query: {}", query);
    }

    public void logRequest(HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        MDC.put("session_id", sessionId);  // MDC에 저장

        log.info("User action logged"); // 이후 로그에 자동 포함됨
    }
}
