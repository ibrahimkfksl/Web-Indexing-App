package com.yazlab.web.jsoup.Question1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Question1 {

    private List<WordFrequency> kelimeList = new ArrayList<>();


    public List<WordFrequency> freakansBul(String url) {
        System.out.println("Question1 Function Run...");
        try {
            Document document = Jsoup.connect(url).get();
            Elements broadcasts = document.select("body");
            kelimelereBol(broadcasts.text());
        } catch (Exception e) {
        }
        System.out.println("Question1 Function Finish...");
        return kelimeList;
    }


    private void kelimelereBol(String text) {
        String[] kelimeler;
        kelimeler = text.split(" ");
        for (String kelime : kelimeler) {
            int i = 0;
            Boolean kontrol = false;
            kelime = kelime.toLowerCase();
            for (i = 0; i < kelimeList.size(); i++) {
                if (kelime.equals(kelimeList.get(i).getKelime())) {
                    kelimeList.get(i).freakansArtir();
                    kontrol = true;
                    break;
                }
            }
            if (!kontrol) {
                kelimeList.add(new WordFrequency(kelime));
            }
        }
    }
}
