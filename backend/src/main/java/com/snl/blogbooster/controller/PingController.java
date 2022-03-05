package com.snl.blogbooster.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
public class PingController {
    @GetMapping(value= "/ping")
    public String insertInfluenceKeyword()
    {
        return "server is alive!";
    }
}
