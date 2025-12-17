package com.sykmm.realestate.models;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int id;

    private Title title;

    private Content content;

    @SerializedName("_embedded")
    private Embedded embedded;

    public int getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public Embedded getEmbedded() {
        return embedded;
    }
}
