package com.aj.wikisearchapp.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Query implements Serializable
{

    @SerializedName("pages")
    @Expose
    private List<Page> pages = null;
    private final static long serialVersionUID = -7464202565176972403L;

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

}