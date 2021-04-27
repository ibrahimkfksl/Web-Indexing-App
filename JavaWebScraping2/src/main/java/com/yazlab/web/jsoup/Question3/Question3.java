package com.yazlab.web.jsoup.Question3;


import com.yazlab.web.jsoup.Question1.Question1;
import com.yazlab.web.jsoup.Question1.WordFrequency;
import com.yazlab.web.jsoup.Question2.Keywords;
import com.yazlab.web.jsoup.Question2.Question2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question3 {

    private List<WordFrequency> urlFrequencyExport;


    public List<WordFrequency> getUrlFrequencyExport() {
        return urlFrequencyExport;
    }

    private List<Similarity> similarUrl = new ArrayList<>();

    public List<Similarity> getSimilarUrl() {
        return similarUrl;
    }

    public List<Similarity> similarity(String url1, String url2) {
        System.out.println("Question3 Function Run...");

        try {

            double resultSimilarity = 0.0;
            resultSimilarity = calculateSimilarity(url1, url2);

            print(url1, resultSimilarity);
            print(url2, resultSimilarity);

        } catch (Exception e) {
        }

        System.out.println("Question3 Function Finish...");
        return similarUrl;
    }

    public void print(String url, double resultSimilarity) {

        String[] urlKeywords = new String[7];
        int[] urlKeywordsFrequency = new int[7];

        Question1 Question1 = new Question1();
        List<WordFrequency> urlFrequency = Question1.freakansBul(url);
        this.urlFrequencyExport = urlFrequency;

        Question2 question2 = new Question2();
        List<Keywords> keywords = new ArrayList<>();
        keywords = question2.extractKeywords(url);


        //url keywords
        int counter = 0;
        for (int j = 0; j < 7; j++) {
            if (!Arrays.asList(urlKeywords).contains(keywords.get(counter).getWord())) {
                urlKeywords[j] = keywords.get(counter).getWord();
                counter++;
            } else {
                j--;
                counter++;
            }
        }

        //Find keywords frequency
        counter = 0;
        for (int j = 0; j < 7; j++) {
            for (WordFrequency s : urlFrequency) {
                if (urlKeywords[j].equals(s.getKelime())) {
                    urlKeywordsFrequency[j] = s.getFreakans();
                }
            }
        }


        similarUrl.add(new Similarity(urlKeywords, urlKeywordsFrequency, resultSimilarity, url));

    }

    //1. url de 1. url in anahtar kelimelerinin toplam geçme sayısını al, 2. url de 1. url anahtar kelimelerinin toplam geçme sayısını bul. yüzdelik olarak dönder
    public double calculateSimilarity(String url1, String url2) {

        String[] urlKeywords = new String[7];
        int[] urlKeywordsFrequency = new int[7];
        Question1 Question1 = new Question1();
        List<WordFrequency> urlFrequency = Question1.freakansBul(url1);
        Question2 question2 = new Question2();
        List<Keywords> keywords = new ArrayList<>();
        keywords = question2.extractKeywords(url1);

        //url1 keywords
        int counter = 0;
        for (int j = 0; j < 7; j++) {
            if (!Arrays.asList(urlKeywords).contains(keywords.get(counter).getWord())) {
                urlKeywords[j] = keywords.get(counter).getWord();
                counter++;
            } else {
                j--;
                counter++;
            }
        }

        //Find keywords frequency at url1
        counter = 0;
        double url1TotalFrequency = 0.0;
        for (int j = 0; j < 7; j++) {
            for (WordFrequency s : urlFrequency) {
                if (urlKeywords[j].equals(s.getKelime())) {
                    url1TotalFrequency += s.getFreakans();
                }
            }
        }

        //url1 anahtar kelimeleri url2 de kaç defa geçiyor
        Question1 Question1_1 = new Question1();
        List<WordFrequency> urlFrequency2 = Question1_1.freakansBul(url2);
        double url2TotalFrequency = 0.0;
        for (int j = 0; j < 7; j++) {
            for (WordFrequency s : urlFrequency2) {
                if (urlKeywords[j].equals(s.getKelime())) {
                    url2TotalFrequency += s.getFreakans();
                }
            }
        }

        double result = (url2TotalFrequency / url1TotalFrequency) * 100;
        if (result > 100) {
            result = 100.0;
        }

        return result;
    }

}
