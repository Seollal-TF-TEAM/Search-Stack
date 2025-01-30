package com.example.searchstack.controller;

import com.example.searchstack.config.exception.ApiException;
import com.example.searchstack.config.exception.ErrorCode;
import com.example.searchstack.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/new")

    public ResponseEntity<Object> newSearchEnter(@RequestParam @Validated String query) throws URISyntaxException {
        String redirectUrl = "http://localhost:8080/main?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI(redirectUrl));

        if (query.isEmpty() || query == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST_ERROR);
        }
        searchService.search(query);

        return new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
    }
}
