package com.Nextechbd.bizcards;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import retrofit2.http.Query;

public interface Galaryfront {
    @GET("frontgalary.php")
    Call<List<Galaryresponse>> getStringScalar(@Query("id") String id);
}
