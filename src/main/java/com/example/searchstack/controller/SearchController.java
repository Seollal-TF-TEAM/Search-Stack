package com.example.searchstack.controller;

import com.example.searchstack.config.exception.ApiException;
import com.example.searchstack.config.exception.ErrorCode;
import com.example.searchstack.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/new")
    public ResponseEntity<String> newSearchEnter(@RequestParam @Validated String query) {
        if (query.equals("") || query == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST_ERROR);
        }
        searchService.search(query);

        return ResponseEntity.ok("ok");
    }
}
