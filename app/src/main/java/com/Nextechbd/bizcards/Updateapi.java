package com.Nextechbd.bizcards;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Updateapi {
    @FormUrlEncoded
    //@GET("update_product.php")
    //Call<String> update(@Query("price") String price, @Query("product_id") String userid);

    @POST("update.php")
    Call<UpdatBody> update(@Field("id") String id, @Field("name") String name, @Field("job") String job, @Field("company") String company,
                           @Field("phone") String phone, @Field("email") String email, @Field("website") String website, @Field("address") String address);
}
