package com.cassianomenezes.mytransferwise.entries;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FootballResponse implements Parcelable{

    @SerializedName("players")
    private List<Players> playersList;

    protected FootballResponse(Parcel in) {
        playersList = in.createTypedArrayList(Players.CREATOR);
    }

    public static final Creator<FootballResponse> CREATOR = new Creator<FootballResponse>() {
        @Override
        public FootballResponse createFromParcel(Parcel in) {
            return new FootballResponse(in);
        }

        @Override
        public FootballResponse[] newArray(int size) {
            return new FootballResponse[size];
        }
    };

    public List<Players> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<Players> playersList) {
        this.playersList = playersList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(playersList);
    }
}
