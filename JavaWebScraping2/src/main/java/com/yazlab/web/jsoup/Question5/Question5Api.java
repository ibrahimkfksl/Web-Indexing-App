package com.yazlab.web.jsoup.Question5;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/soru5")
public class Question5Api {

    @GetMapping("/synonyms")
    public ArrayList<Object> get(@RequestParam String mainUrl, @RequestParam List<String> urlSet) {
        System.out.println("Question5 Api Run...\n\n\n");
        Question5 question5 = new Question5();
        return question5.synonymsWords(mainUrl, urlSet);
    }

}
