package com.cassianomenezes.mytransferwise.network;

import com.cassianomenezes.mytransferwise.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private RetrofitModel model;

    public RetrofitClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request().newBuilder().build();
            return chain.proceed(request);
        }).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .client(okHttpClient)
                .build();
        model = retrofit.create(RetrofitModel.class);
    }

    public RetrofitModel getModel() {
        return  model;
    }
}
