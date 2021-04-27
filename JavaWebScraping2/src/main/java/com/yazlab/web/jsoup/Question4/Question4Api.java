
package com.yazlab.web.jsoup.Question4;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/soru4")
public class Question4Api {


    @GetMapping("/indexandsort")
    public List<UrlTree> get(@RequestParam String mainUrl, @RequestParam List<String> urlSet){
        System.out.println("Question4 Api Run...\n\n\n");
        Question4 question4=new Question4();
       return question4.indexing(mainUrl,urlSet);
    }
}