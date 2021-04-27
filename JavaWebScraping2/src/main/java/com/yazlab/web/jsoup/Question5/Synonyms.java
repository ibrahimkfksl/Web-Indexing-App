package com.yazlab.web.jsoup.Question5;

import java.util.ArrayList;
import java.util.HashMap;

public class Synonyms {

    private HashMap<String, ArrayList<String>> list;
    private String url;

    public Synonyms(HashMap<String, ArrayList<String>> list, String url) {
        this.list = list;
        this.url = url;
    }

    public HashMap<String, ArrayList<String>> getList() {
        return list;
    }

    public void setList(HashMap<String, ArrayList<String>> list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
