package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface creatapi {
    @FormUrlEncoded
    @POST("profile.php")
    Call<Recordresponse> getStringScalar(@Field("image") String image, @Field("name") String name, @Field("job_title") String job_title, @Field("company") String company,
                                         @Field("phone") String phone, @Field("email") String email, @Field("website") String website,@Field("address") String address,@Field("id") String id );


}
