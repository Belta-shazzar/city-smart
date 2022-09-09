package com.shazzar.citysmart;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class V1Controller {

    @GetMapping
    public String citySmart() {
        return "City Smart: work in progress...";
    }

}

