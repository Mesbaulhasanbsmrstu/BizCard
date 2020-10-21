package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Saveapi {
    @FormUrlEncoded
    @POST("insert.php")
    Call<Recordresponse> getStringScalar(@Field("image") String image,@Field("details") String details, @Field("phone1") String phone1, @Field("phone2") String phone2,
                                                 @Field("email1") String email1, @Field("email2") String email2, @Field("id") String id );

}
