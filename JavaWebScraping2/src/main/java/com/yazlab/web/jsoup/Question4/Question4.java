package com.yazlab.web.jsoup.Question4;

import com.yazlab.web.jsoup.Question2.Keywords;
import com.yazlab.web.jsoup.Question2.Question2;
import com.yazlab.web.jsoup.Question3.Question3;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Question4 {


    private String mainUrls;
    private List<UrlTree> urls = new ArrayList<>();
    private final int limiter = 4;


    public List<UrlTree> indexing(String mainUrls, List<String> urlSet) {
        System.out.println("Question4 Function Run...");
        this.mainUrls = mainUrls;
        for (int i = 0; i < urlSet.size(); i++) {
            UrlTree url = new UrlTree(urlSet.get(i), 1);
            this.urls.add(url);
        }

        try {

            getAllUrl();
            calculateScore();
            keywordFrequency();
            allUrlTreeSort();


        } catch (Exception e) {
        }
        System.out.println("Question4 Function Finish...");
        return urls;
    }

    public void getAllUrl() throws IOException {

        for (int i = 0; i < urls.size(); i++) {
            Document document = null;
            Elements links = null;
            Element link;

            try {
                document = Jsoup.connect(urls.get(i).getUrl()).get();
                links = document.select("a");

            } catch (Exception e) {
            }


            for (int j = 0; j < links.size(); j++) {

                link = links.get(j);
                UrlTree linkClass = new UrlTree(link.attr("href"), 2);
                if (linkClass.getUrl().startsWith("#") || linkClass.getUrl().contains("javascript:void(0)") || linkClass.getUrl().equals("/")) {
                    continue;
                }
                if (linkClass.getUrl().startsWith("/")) {
                    linkClass.setUrl(link.attr("abs:href") + linkClass.getUrl());
                }

                linkClass.setUpperUrl(urls.get(i));
                if (canAddSubUrl(linkClass)) {
                    urls.get(i).addSubUrl(linkClass);
                }
                if (urls.get(i).getSubUrl().size() == limiter) {
                    break;
                }


            }

            for (int j = 0; j < urls.get(i).getSubUrl().size(); j++) {

                try {
                    document = Jsoup.connect(urls.get(i).getSubUrl().get(j).getUrl()).get();
                    links = document.select("a");
                } catch (Exception e) {
                }
                for (int k = 0; k < links.size(); k++) {

                    link = links.get(k);
                    UrlTree linkClass = new UrlTree(link.attr("href"), 3);
                    if (linkClass.getUrl().startsWith("#") || linkClass.getUrl().contains("javascript:void(0)") || linkClass.getUrl().equals("/")) {
                        continue;
                    }
                    if (linkClass.getUrl().startsWith("/")) {
                        linkClass.setUrl(link.attr("abs:href") + linkClass.getUrl());
                    }

                    linkClass.setUpperUrl(urls.get(i).getSubUrl().get(j));

                    if (canAddSubUrl(linkClass)) {
                        urls.get(i).getSubUrl().get(j).addSubUrl(linkClass);
                    }
                    if (urls.get(i).getSubUrl().get(j).getSubUrl().size() == limiter) {
                        break;
                    }


                }


            }


        }
    }

    public Boolean canAddSubUrl(UrlTree url) {

        try {
            URL testUrl = new URL(url.getUrl());
            HttpURLConnection connection = (HttpURLConnection) testUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int code = connection.getResponseCode();
            if (code != 200) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        if (url.getLevel() == 2) {
            if (url.getUpperUrl().getSubUrl().isEmpty()) {
                return true;
            }
            for (int i = 0; i < url.getUpperUrl().getSubUrl().size(); i++) {
                if (url.getUrl().equals(url.getUpperUrl().getSubUrl().get(i).getUrl())) {
                    return false;
                }
            }
            return true;
        }
        if (url.getLevel() == 3) {
            for (int i = 0; i < url.getUpperUrl().getUpperUrl().getSubUrl().size(); i++) {
                if (url.getUrl().equals(url.getUpperUrl().getUpperUrl().getSubUrl().get(i).getUrl())) {
                    return false;
                }
            }
            for (int i = 0; i < url.getUpperUrl().getUpperUrl().getSubUrl().size(); i++) {
                if (!url.getUpperUrl().getUpperUrl().getSubUrl().get(i).getSubUrl().isEmpty()) {
                    for (int j = 0; j < url.getUpperUrl().getUpperUrl().getSubUrl().get(i).getSubUrl().size(); j++) {
                        if (url.getUrl().equals(url.getUpperUrl().getUpperUrl().getSubUrl().get(i).getSubUrl().get(j).getUrl())) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }


    public void calculateScore() {
        calculateIndividualScore();
        calculateTotalScore();
    }


    public void calculateIndividualScore() {


        System.out.println("Calculate Individual Score");
        System.out.println("Websites: ");
        for (int i = 0; i < urls.size(); i++) {
            try {
                Question3 question3 = new Question3();
                urls.get(i).setIndividualScore(question3.similarity(mainUrls, urls.get(i).getUrl()).get(1).getSimilarity());
                urls.get(i).setAllWordFrequency(question3.getUrlFrequencyExport());
                question3.getSimilarUrl().get(0).setSimilarity(0);
                question3.getSimilarUrl().get(1).setSimilarity(0);
            } catch (Exception e) {
            }

            for (int j = 0; j < urls.get(i).getSubUrl().size(); j++) {
                try {
                    Question3 question3_2 = new Question3();
                    urls.get(i).getSubUrl().get(j).setIndividualScore(question3_2.similarity(mainUrls, urls.get(i).getSubUrl().get(j).getUrl()).get(1).getSimilarity());
                    urls.get(i).getSubUrl().get(j).setAllWordFrequency(question3_2.getUrlFrequencyExport());
                    question3_2.getSimilarUrl().get(0).setSimilarity(0);
                    question3_2.getSimilarUrl().get(1).setSimilarity(0);
                } catch (Exception e) {
                }

                for (int k = 0; k < urls.get(i).getSubUrl().get(j).getSubUrl().size(); k++) {
                    try {
                        Question3 question3_3 = new Question3();
                        urls.get(i).getSubUrl().get(j).getSubUrl().get(k).setIndividualScore(question3_3.similarity(mainUrls, urls.get(i).getSubUrl().get(j).getSubUrl().get(k).getUrl()).get(1).getSimilarity());
                        urls.get(i).getSubUrl().get(j).getSubUrl().get(k).setAllWordFrequency(question3_3.getUrlFrequencyExport());
                        urls.get(i).getSubUrl().get(j).getSubUrl().get(k).setTotalScore(urls.get(i).getSubUrl().get(j).getSubUrl().get(k).getIndividualScore());
                        question3_3.getSimilarUrl().get(0).setSimilarity(0);
                        question3_3.getSimilarUrl().get(1).setSimilarity(0);
                        System.out.println("Website  :" + (i) + "---" + (j) + "---" + (k) + "---");
                    } catch (Exception e) {
                    }

                }
            }
        }
    }

    public void calculateTotalScore() {
        /*
         * 1-2 arasinda puan bolumu 60-40
         * 2-3 arasinda puan bolumu 70-30
         */

        double scoreLevel2 = 0;
        double scoreLevel3 = 0;

        for (int i = 0; i < urls.size(); i++) {
            for (int j = 0; j < urls.get(i).getSubUrl().size(); j++) {
                for (int k = 0; k < urls.get(i).getSubUrl().get(j).getSubUrl().size(); k++) {
                    try {
                        scoreLevel3 += urls.get(i).getSubUrl().get(j).getSubUrl().get(k).getIndividualScore();
                    } catch (Exception e) {
                    }

                }

                try {
                    scoreLevel3 /= urls.get(i).getSubUrl().get(j).getSubUrl().size();
                    if (Double.isInfinite(scoreLevel3)) {
                        scoreLevel3 = 0;
                    }
                    urls.get(i).getSubUrl().get(j).setTotalScore(((urls.get(i).getSubUrl().get(j).getIndividualScore() * 70) / 100) + ((scoreLevel3 * 30) / 100));
                    scoreLevel2 += urls.get(i).getSubUrl().get(j).getTotalScore();
                } catch (Exception e) {
                }

            }

            try {
                scoreLevel2 /= urls.get(i).getSubUrl().size();
                if (Double.isInfinite(scoreLevel2)) {
                    scoreLevel2 = 0;
                }
                urls.get(i).setTotalScore(((urls.get(i).getIndividualScore() * 60) / 100) + ((scoreLevel2 * 40) / 100));
            } catch (Exception e) {
            }


        }
    }


    public void keywordFrequency() {
        /*
         * calculateIndividual metodu calismadan calismaz
         */
        Question2 question2 = new Question2();
        List<Keywords> keywords;

        keywords = question2.extractKeywords(mainUrls);
        for (int i = 0; i < urls.size(); i++) {
            try {
                compareKeyword(keywords, urls.get(i));
            } catch (Exception e) {
            }
            for (int j = 0; j < urls.get(i).getSubUrl().size(); j++) {
                try {
                    compareKeyword(keywords, urls.get(i).getSubUrl().get(j));
                } catch (Exception e) {
                }

                for (int k = 0; k < urls.get(i).getSubUrl().get(j).getSubUrl().size(); k++) {
                    try {
                        compareKeyword(keywords, urls.get(i).getSubUrl().get(j).getSubUrl().get(k));
                    } catch (Exception e) {
                    }

                }
            }
        }


    }

    public void compareKeyword(List<Keywords> keywords, UrlTree url) {
        for (int i = 0; i < keywords.size(); i++) {

            if (i == 7) {
                break;
            }
            KeywordFrequency keywordsFrequeny = new KeywordFrequency();
            keywordsFrequeny.setKeyword(keywords.get(i).getWord());
            keywordsFrequeny.setFrequency(0);
            url.addKeywordFrequency(keywordsFrequeny);
        }


        for (int i = 0; i < url.getKeywordFrequency().size(); i++) {

            for (int j = 0; j < url.getAllWordFrequency().size(); j++) {
                if (url.getKeywordFrequency().get(i).getKeyword().equals(url.getAllWordFrequency().get(j).getKelime())) {
                    url.getKeywordFrequency().get(i).setFrequency(url.getAllWordFrequency().get(j).getFreakans());
                }
            }
        }
    }

    public void allUrlTreeSort() {
        for (int i = 0; i < urls.size(); i++) {
            sort(urls);
        }

        for (int i = 0; i < urls.size(); i++) {
            sort(urls.get(i).getSubUrl());
        }

        for (int i = 0; i < urls.size(); i++) {
            for (int j = 0; j < urls.get(i).getSubUrl().size(); j++) {
                sort(urls.get(i).getSubUrl().get(j).getSubUrl());
            }
        }
    }

    public void sort(List<UrlTree> url) {
        int n = url.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (url.get(j).getTotalScore() < url.get(j + 1).getTotalScore()) {
                    UrlTree temp = url.get(j);
                    url.set(j, url.get(j + 1));
                    url.set(j + 1, temp);
                }
    }


}