package com.sykmm.realestate.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Embedded {

    @SerializedName("wp:featuredmedia")
    private List<FeaturedMedia> featuredMedia;

    public List<FeaturedMedia> getFeaturedMedia() {
        return featuredMedia;
    }
}
