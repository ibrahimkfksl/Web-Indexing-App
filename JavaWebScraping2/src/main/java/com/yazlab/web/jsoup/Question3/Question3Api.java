package com.yazlab.web.jsoup.Question3;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/soru3")
public class Question3Api {

    @GetMapping("/similarity")
    public List<Similarity> get(@RequestParam("url1") String url1, @RequestParam("url2") String url2){
        System.out.println("Question3 Api Run...\n\n\n");
        Question3 question3 = new Question3();
        return question3.similarity(url1,url2);
    }
}
