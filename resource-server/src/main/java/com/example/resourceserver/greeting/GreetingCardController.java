package com.example.resourceserver.greeting;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingCardController {


    @GetMapping("/greeting")
        public String greeting(@AuthenticationPrincipal UserDetails user) {
        return "Welcome, " + user.getUsername();
    }

    @GetMapping("/overview")
    public String overview(){
        return "This is an overview.";
    }

    @GetMapping("/nearest-store")
    public String getNearestStore(@AuthenticationPrincipal GreetingCardUser user){
        return user.getNearestStore();
    }

    @PostMapping("/purchase")
    public void purchase(@AuthenticationPrincipal GreetingCardUser user, @RequestBody String card){
        System.out.println("user = " + user + ", card = " + card);
    }
}
