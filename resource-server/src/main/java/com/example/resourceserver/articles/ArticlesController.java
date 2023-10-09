package com.example.resourceserver.articles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticlesController {

    @GetMapping("/articles")
    public String[] getArticles(){
        return new String[] {"article1", "article2", "article3"};
    }
}
