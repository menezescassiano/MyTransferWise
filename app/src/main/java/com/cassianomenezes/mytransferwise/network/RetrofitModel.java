package com.cassianomenezes.mytransferwise.network;

import com.cassianomenezes.mytransferwise.entries.Beer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitModel {

    @GET("v2/beers")
    Call<List<Beer>> getBeerInfo();

}
