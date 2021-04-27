package com.yazlab.web.jsoup.Question3;

public class Similarity {
    private double similarity;
    private int[] frequency;
    private String[] keywords;
    private String url;

    public Similarity(String[] keywords, int[] frequency, double similarity, String url) {
        this.similarity = similarity;
        this.frequency = frequency;
        this.keywords = keywords;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public int[] getFrequency() {
        return frequency;
    }

    public void setFrequency(int[] frequency) {
        this.frequency = frequency;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

}
