package com.cassianomenezes.mytransferwise.network;

import com.cassianomenezes.mytransferwise.entries.BeerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitModel {

    @GET("v2/beers")
    Call<List<BeerResponse>> getBeerInfo();

}
