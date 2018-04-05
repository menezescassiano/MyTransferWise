package com.cassianomenezes.mytransferwise.network;

import com.cassianomenezes.mytransferwise.entries.Beer;
import com.cassianomenezes.mytransferwise.entries.FootballResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitModel {

    @GET("v1/teams/5/players")
    Call<FootballResponse> getPlayersInfo();

    @GET("v2/beers")
    Call<List<Beer>> getBeerInfo();

}
