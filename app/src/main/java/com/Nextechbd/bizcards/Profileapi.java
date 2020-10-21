package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Profileapi {
    @GET("fetch_profile.php")
    Call<getprofileresponse> getStringScalar(@Query("id") String id);
}
