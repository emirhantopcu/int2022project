package com.example.int2022.controller;

import com.example.int2022.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SearchService searchService;
    @GetMapping()
    public ResponseEntity<?> search(@RequestParam("query") String query){
        return searchService.search(query);
    }
}
