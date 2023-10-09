package com.example.resourceserver.support;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/support")
public class SupportController {

    @GetMapping("/card")
    public String support(){
        return "You do not get support.";
    }
}
