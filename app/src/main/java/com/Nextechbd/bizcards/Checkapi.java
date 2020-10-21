package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Checkapi {
    @GET("check_profile.php")
    Call<Verifyresponse> getStringScalar(@Query("id") String id);
}
