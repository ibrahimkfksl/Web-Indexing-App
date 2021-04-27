package com.yazlab.web.jsoup.Question4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yazlab.web.jsoup.Question1.WordFrequency;

import java.util.ArrayList;
import java.util.List;

public class UrlTree {
    private String url;
    private int level;
    @JsonIgnore
    private double individualScore;
    private double totalScore;
    @JsonIgnore
    private List<WordFrequency> allWordFrequency = new ArrayList<>();
    private List<KeywordFrequency> keywordFrequency = new ArrayList<>();
    @JsonIgnore
    private UrlTree upperUrl = null;
    private List<UrlTree> subUrl = new ArrayList<>();


    public void addKeywordFrequency(KeywordFrequency keywordFrequency) {
        this.keywordFrequency.add(keywordFrequency);
    }

    public List<KeywordFrequency> getKeywordFrequency() {
        return keywordFrequency;
    }

    public void setKeywordFrequency(List<KeywordFrequency> keywordFrequency) {
        this.keywordFrequency = keywordFrequency;
    }

    public List<WordFrequency> getAllWordFrequency() {
        return allWordFrequency;
    }

    public void setAllWordFrequency(List<WordFrequency> allWordFrequency) {
        this.allWordFrequency = allWordFrequency;
    }


    public UrlTree(String url, int level) {
        this.url = url;
        this.level = level;
    }

    public Boolean addSubUrl(UrlTree url) {
        this.subUrl.add(url);
        return true;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public UrlTree getUpperUrl() {
        return upperUrl;
    }

    public void setUpperUrl(UrlTree upperUrl) {
        this.upperUrl = upperUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<UrlTree> getSubUrl() {
        return subUrl;
    }

    public void setSubUrl(List<UrlTree> subUrl) {
        this.subUrl = subUrl;
    }

    public double getIndividualScore() {
        return individualScore;
    }

    public void setIndividualScore(double individualScore) {
        this.individualScore = individualScore;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

}
