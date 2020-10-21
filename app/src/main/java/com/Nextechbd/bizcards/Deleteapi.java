package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Deleteapi {
    @FormUrlEncoded
    @POST("delete.php")
    Call<Deleteresponse> delete(@Field("card_id")  String id,@Field("image") String image);
}
