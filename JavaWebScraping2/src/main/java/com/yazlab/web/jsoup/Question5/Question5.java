package com.yazlab.web.jsoup.Question5;

import com.yazlab.web.jsoup.Question2.Keywords;
import com.yazlab.web.jsoup.Question2.Question2;
import com.yazlab.web.jsoup.Question4.Question4;
import com.yazlab.web.jsoup.Question4.UrlTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Question5 {

    private HashMap<String, ArrayList<String>> synonymsList = new HashMap<>();
    private List<Keywords> keywords = new ArrayList<>();
    private List<Synonyms> synonyms = new ArrayList<>();
    private ArrayList<Object> classes = new ArrayList<>();
    private HashMap<String, ArrayList<String>> sWords;

    public ArrayList<Object> synonymsWords(String mainUrl, List<String> urlList) {
        System.out.println("Question5 Function Run...");

        readFiles();
        findKeywords(mainUrl);
        for (int i = 0; i < urlList.size(); i++) {
            findKeywords(urlList.get(i));
        }

        Question4 question4 = new Question4();
        List<UrlTree> tree = question4.indexing(mainUrl, urlList);

        UrlTree mainUrlTree = new UrlTree(mainUrl, 0);
        mainUrlTree.setSubUrl(tree);

        classes.add(synonyms);
        classes.add(mainUrlTree);
        System.out.println("Question5 Function Finish...");
        return classes;
    }

    public void findKeywords(String url) {
        Question2 question2 = new Question2();
        keywords = question2.extractKeywords(url);
        findSynonyms(url);
    }

    public void readFiles() {
        File f = new File("src/main/java/com/yazlab/web/jsoup/Question5/kelime.txt");
        Scanner file = null;
        try {
            file = new Scanner(f);
        } catch (FileNotFoundException e) {

        }
        String[] vocabulary;
        while (file.hasNextLine()) {
            vocabulary = file.nextLine().split("\\s+");
            if (vocabulary.length != 2) {
                continue;
            } else {
                if (synonymsList.get(vocabulary[0]) == null) {
                    synonymsList.put(vocabulary[0].trim().toLowerCase(), new ArrayList<String>());
                }
                synonymsList.get(vocabulary[0]).add(vocabulary[1]);
            }
        }
    }

    public void findSynonyms(String url) {
        sWords = new HashMap<>();
        String[] words = new String[7];
        for (int i = 0; i < 7; i++) {
            words[i] = keywords.get(i).getWord();
        }

        for (int i = 0; i < words.length; i++) {
            if (synonymsList.get(words[i]) != null) {
                sWords.put(words[i], synonymsList.get(words[i]));
            } else {
                sWords.put(words[i], new ArrayList<>());
                sWords.get(words[i]).add(words[i]);
            }
        }

        synonyms.add(new Synonyms(sWords, url));
    }
}
