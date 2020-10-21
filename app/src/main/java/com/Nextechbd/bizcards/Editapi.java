package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Editapi {
    @FormUrlEncoded
    @POST("update1.php")
    Call<UpdatBody> editdata(@Field("id") String id, @Field("name") String name, @Field("job") String job, @Field("company") String company,
                             @Field("phone") String phone, @Field("email") String email, @Field("website") String website, @Field("address") String address, @Field("image") String image);
}
