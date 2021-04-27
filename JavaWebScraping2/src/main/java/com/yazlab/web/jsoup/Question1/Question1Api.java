package com.yazlab.web.jsoup.Question1;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/soru1")
public class Question1Api {


    @GetMapping("/frekansbul")
    public List<WordFrequency> get(@RequestParam("url") String url) {
        System.out.println("Question1 Api Run...\n\n\n");
        Question1 soru = new Question1();
        return soru.freakansBul(url);
    }
}
