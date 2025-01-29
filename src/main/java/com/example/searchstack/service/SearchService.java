package com.example.searchstack.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
@Service
public class SearchService {

    public void search(String query) {
        log.debug("Search query: {}", query);
    }

    public void logToLogstash(String keyword) {
        String jsonLog = "{\"keyword\": " + keyword + "}";
        try (Socket socket = new Socket("localhost", 5044);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(jsonLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
