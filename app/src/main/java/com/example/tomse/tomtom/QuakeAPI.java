package com.example.tomse.tomtom;

import Model.Feed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface QuakeAPI {

    String BASE_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/";

    @Headers("Content-Type: application/json")
    @GET("query")
    Call<Feed> getData(
            @Query("format") String geojson,
            @Query("eventtype") String earthquake,
            @Query("orderby") String time,
            @Query("minmag") Integer six,
            @Query("limit") Integer ten
    );


}
