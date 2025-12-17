package com.sykmm.realestate.models;

import com.google.gson.annotations.SerializedName;

public class FeaturedMedia {

    @SerializedName("source_url")
    private String sourceUrl;

    public String getSourceUrl() {
        return sourceUrl;
    }
}
