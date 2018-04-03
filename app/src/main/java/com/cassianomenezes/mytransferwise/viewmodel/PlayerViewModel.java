package com.cassianomenezes.mytransferwise.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.cassianomenezes.mytransferwise.entries.Player;

public class PlayerViewModel {

    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> position = new ObservableField<>();
    private ObservableField<Integer> jerseyNumber = new ObservableField<>();
    private ObservableField<String> dateOfBirth = new ObservableField<>();
    private ObservableField<String> nationality = new ObservableField<>();
    private ObservableField<String> contractUntil = new ObservableField<>();

    private Context context;
    private Player player;

    public PlayerViewModel(Context context, Player player) {
        this.context = context;
        this.player = player;
        handlePlayerData();
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public ObservableField<Integer> getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber.set(jerseyNumber);
    }

    public ObservableField<String> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public ObservableField<String> getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public ObservableField<String> getContractUntil() {
        return contractUntil;
    }

    public void setContractUntil(String contractUntil) {
        this.contractUntil.set(contractUntil);
    }

    private void handlePlayerData() {
        setName(player.getName());
        setPosition(player.getPosition());
        setJerseyNumber(player.getJerseyNumber());
        setDateOfBirth(player.getDateOfBirth());
        setContractUntil(player.getContractUntil());
    }
}
