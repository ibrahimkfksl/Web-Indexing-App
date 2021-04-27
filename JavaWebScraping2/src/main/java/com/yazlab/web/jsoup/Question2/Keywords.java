package com.yazlab.web.jsoup.Question2;

public class Keywords {

    private String word;
    private double keywords;
    public Keywords(String word,  double keywords){
        this.word = word;
        this.keywords = keywords;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public double getKeywords() {
        return keywords;
    }

    public void setKeywords(double keywords) {
        this.keywords = keywords;
    }
}
