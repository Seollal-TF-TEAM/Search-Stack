package com.example.searchstack.service;

import com.example.searchstack.config.exception.ApiException;
import com.example.searchstack.config.exception.ErrorCode;
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
        if (query.isEmpty() || query == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST_ERROR);
        }
        log.debug("Search query: {}", query);
    }

}
