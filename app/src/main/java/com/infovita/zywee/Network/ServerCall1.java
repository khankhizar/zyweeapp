package com.infovita.zywee.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infovita.zywee.Pojo.Coupon;
import com.infovita.zywee.Sharedvalues.Serverdatas;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Khizarkhan on 27-07-2017.
 */

public interface ServerCall1 {
    Serverdatas sd = Serverdatas.getSingletonObject();
    String url = sd.url1;

    @FormUrlEncoded
    @POST("Hospitals/coupons")
    Call<Coupon> couponcheck(@Field("code") String code);

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    OkHttpClient client = new OkHttpClient();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .callbackExecutor(Executors.newFixedThreadPool(1))
            .build();
}
