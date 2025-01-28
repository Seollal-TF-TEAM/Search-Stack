package com.example.searchstack.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    @PostMapping("/new")
    public ResponseEntity<String> newSearchEnter(@RequestParam @Validated String key) {

        return ResponseEntity.ok("ok");
    }
}
