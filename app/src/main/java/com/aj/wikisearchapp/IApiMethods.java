package com.aj.wikisearchapp;

import com.aj.wikisearchapp.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApiMethods {

    @GET("api.php?")
    Call<SearchResult> groupList(@Query("action") String groupId, @Query("format") String format, @Query("formatversion") String formatVersion,
                                 @Query("generator") String generator, @Query("gpssearch") String gpssearch,
                                 @Query("gpslimit") String gpslimit, @Query("prop") String prop,
                                 @Query("piprop") String piprop, @Query("pithumbsize") String pithumbsize,
                                 @Query("pilimit") String pilimit, @Query("redirects") String redirects,
                                 @Query("wbptterms") String wbptterms);


}
