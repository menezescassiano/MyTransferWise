package com.cassianomenezes.mytransferwise.entries;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Player extends BaseObservable implements Parcelable{

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private String position;

    @SerializedName("jerseyNumber")
    private int jerseyNumber;

    @SerializedName("dateOfBirth")
    private String dateOfBirth;

    @SerializedName("nationality")
    private String nationality;

    @SerializedName("contractUntil")
    private String contractUntil;

    public Player(){

    }

    protected Player(Parcel in) {
        name = in.readString();
        position = in.readString();
        jerseyNumber = in.readInt();
        dateOfBirth = in.readString();
        nationality = in.readString();
        contractUntil = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(position);
        dest.writeInt(jerseyNumber);
        dest.writeString(dateOfBirth);
        dest.writeString(nationality);
        dest.writeString(contractUntil);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getContractUntil() {
        return contractUntil;
    }

    public void setContractUntil(String contractUntil) {
        this.contractUntil = contractUntil;
    }
}
