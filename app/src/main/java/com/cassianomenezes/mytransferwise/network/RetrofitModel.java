package com.cassianomenezes.mytransferwise.network;

import com.cassianomenezes.mytransferwise.entries.FootballResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitModel {

    @GET("v1/teams/5/players")
    Call<FootballResponse> getPlayersInfo();

}
