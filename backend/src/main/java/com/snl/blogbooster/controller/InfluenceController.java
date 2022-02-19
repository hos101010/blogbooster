package com.snl.blogbooster.controller;

import com.snl.blogbooster.service.InfluenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class InfluenceController {

    private final InfluenceService influenceService;

    @GetMapping(value= "/v1/data/influence", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertInfluenceKeyword()
    {
        influenceService.insertInflKeyword();
    }
}
