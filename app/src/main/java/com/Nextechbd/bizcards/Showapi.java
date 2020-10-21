package com.Nextechbd.bizcards;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Showapi {
    @GET("fetch_card.php")
    Call<List<Show_element>> getdata(@Query("id") String id,@Query("search") String search);
}