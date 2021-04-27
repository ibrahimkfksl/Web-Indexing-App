package com.yazlab.web.jsoup.Question2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question2 {

    static int numberOfSentences = 0;
    private List<Keywords> keywords = new ArrayList<>();
    private ArrayList<String> sentences = new ArrayList<>();
    private HashMap<String, Integer> score = new HashMap<>();

    public List<Keywords> extractKeywords(String url) {
        System.out.println("Question2 Function Run...");

        try {

            Document document = Jsoup.connect(url).get();

            //get meta keywords and get h tags and add score hashmap and add keywords class
            getMetaKeywords(url);
            getHKeywords(url);
            addKeywordsClass();

            //TFIDF algorithms
            Elements broadcasts = document.select("body");
            //paragraph into sentences regex
            Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
            Matcher reMatcher = re.matcher(broadcasts.text());

            while (reMatcher.find()) {
                sentences.add(reMatcher.group().toString().toLowerCase());
            }
            numberOfSentences = sentences.size();

            for (int i = 0; i < numberOfSentences; i++) {
                findRepetition(sentences.get(i));
            }
            topElements();


        } catch (Exception e) {

        }

        System.out.println("Question2 Function Finish...");
        return keywords;
    }

    public void findRepetition(String sentence) {
        String[] words = sentence.split("\\s+");
        HashMap<String, Integer> keywordList = new HashMap<String, Integer>();


        int sentenceLength = words.length;
        int reWord = 0;

        for (String word : words) {
            word = word.toLowerCase();

            boolean isThere = false;
            for (int i = 0; i < score.size(); i++) {
                if (score.get(word) != null) {
                    isThere = true;
                }
            }
            if (isThere)
                continue;

            if (keywordList.get(word) == null) {
                keywordList.put(word, 1);
            } else {
                int value = (int) keywordList.get(word);
                ++value;
                keywordList.replace(word, value);
            }
            for (String s : sentences) {
                if (s.contains(word)) {
                    reWord++;
                    s.replace(word, "");
                }
            }

        }
        double TF, IDF;
        for (Map.Entry list : keywordList.entrySet()) {
            TF = (int) list.getValue() / (float) sentenceLength;
            IDF = (double) Math.log10(numberOfSentences / (int) reWord);
            if (Double.isInfinite(IDF)) {
                IDF = 0;
            }
            keywords.add(new Keywords(list.getKey().toString(), (TF * IDF)));
        }
    }

    public void topElements() {
        int n = keywords.size();
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (keywords.get(j).getKeywords() < keywords.get(j + 1).getKeywords()) {
                    Keywords temp = keywords.get(j);
                    keywords.set(j, keywords.get(j + 1));
                    keywords.set(j + 1, temp);
                }
    }

    public void getMetaKeywords(String url) throws IOException {


        try {
            Document document = Jsoup.connect(url).get();
            //meta keywords
            String metaKeywords =
                    document.select("meta[name=keywords]").first()
                            .attr("content");

            if (metaKeywords != null) {


                String[] metaArray;
                metaArray = metaKeywords.split(",");

                for (int i = 0; i < metaArray.length; i++) {
                    metaArray[i] = metaArray[i].toLowerCase();
                    if (!metaArray[i].equals(""))
                        score.put(metaArray[i], 7);
                }

            }
        } catch (NullPointerException e) {
            return;
        }

    }

    public void getHKeywords(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        String[] hTagsArray;

        try {
            Elements hTags = document.select("h1");
            if (hTags != null) {
                hTagsArray = hTags.text().split("\\s+");
                setHKeywords(hTagsArray, 6);
            }
        } catch (NullPointerException e) {
            System.out.println("h1 etiketi bulunamadı");
        }

        try {
            Elements hTags = document.select("h2");
            if (hTags != null) {
                hTagsArray = hTags.text().split("\\s+");
                setHKeywords(hTagsArray, 5);
            }
        } catch (NullPointerException e) {
            System.out.println("h2 etiketi bulunamadı");
        }
        try {
            Elements hTags = document.select("h3");
            if (hTags != null) {
                hTagsArray = hTags.text().split("\\s+");
                setHKeywords(hTagsArray, 4);
            }
        } catch (NullPointerException e) {
            System.out.println("h3 etiketi bulunamadı");
        }
        try {
            Elements hTags = document.select("h4");
            if (hTags != null) {
                hTagsArray = hTags.text().split("\\s+");
                setHKeywords(hTagsArray, 3);
            }
        } catch (NullPointerException e) {
            System.out.println("h4 etiketi bulunamadı");
        }
        try {
            Elements hTags = document.select("h5");
            if (hTags != null) {
                hTagsArray = hTags.text().split("\\s+");
                setHKeywords(hTagsArray, 2);
            }
        } catch (NullPointerException e) {
            System.out.println("h5 etiketi bulunamadı");
        }
        try {
            Elements hTags = document.select("h6");
            if (hTags != null) {
                hTagsArray = hTags.text().split("\\s+");
                setHKeywords(hTagsArray, 1);
            }
        } catch (NullPointerException e) {
            System.out.println("h6 etiketi bulunamadı");
        }

    }

    public void setHKeywords(String[] hTagsArray, int point) {
        for (int i = 0; i < hTagsArray.length; i++) {
            hTagsArray[i] = hTagsArray[i].toLowerCase();
            if (!hTagsArray[i].equals("")) {
                for (String name : score.keySet()) {
                    String key = name.toString();
                    String value = score.get(name).toString();
                    if (value.equals(hTagsArray[i])) {
                        score.put(name, score.get(name) + point);
                        break;
                    }
                }
                score.put(hTagsArray[i], point);
            }
        }
    }

    public void addKeywordsClass() {
        for (String add : score.keySet()) {
            String key = add.toString();
            int value = score.get(add);
            keywords.add(new Keywords(key, value));
        }
    }

}