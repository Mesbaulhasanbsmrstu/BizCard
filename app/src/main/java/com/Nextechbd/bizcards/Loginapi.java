package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Loginapi {
    @GET("login.php")
    Call<Loginresponse> getStringScalar(@Query("phone") String phone, @Query("password") String password);

}
