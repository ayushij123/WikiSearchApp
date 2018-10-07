package com.aj.wikisearchapp.model;


import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Terms implements Serializable
{

    @SerializedName("description")
    @Expose
    private List<String> description = null;
    private final static long serialVersionUID = -1979802041126848282L;

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

}