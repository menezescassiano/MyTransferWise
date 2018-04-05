package com.cassianomenezes.mytransferwise.network;

import com.cassianomenezes.mytransferwise.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements Interceptor {

    private RetrofitModel model;
    private static RetrofitClient instance;

    public static RetrofitClient getInstance() {
        if (instance == null)
            instance = new RetrofitClient();

        return instance;
    }

    public RetrofitClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        model = retrofit.create(RetrofitModel.class);
    }

    public RetrofitModel getModel() {
        return  model;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
