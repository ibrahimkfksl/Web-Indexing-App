package com.yazlab.web.jsoup.Question2;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/soru2")
public class Question2Api {

    @GetMapping("/keywords")
    public List<Keywords> get(@RequestParam("url") String url) {
        System.out.println("Question2 Api Run...\n\n\n");
        Question2 question2 = new Question2();

        return question2.extractKeywords(url);
    }
}
