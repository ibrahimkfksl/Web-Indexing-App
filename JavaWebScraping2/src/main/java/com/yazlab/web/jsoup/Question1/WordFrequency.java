package com.yazlab.web.jsoup.Question1;

public class WordFrequency {
    private String kelime;
    private int freakans;

    public WordFrequency(String kelime) {
        this.kelime = kelime;
        this.freakans = 1;
    }

    public void freakansArtir() {
        freakans++;
    }

    public String getKelime() {
        return kelime;
    }

    public void setKelime(String kelime) {
        this.kelime = kelime;
    }

    public int getFreakans() {
        return freakans;
    }

    public void setFreakans(int freakans) {
        this.freakans = freakans;
    }
}
