package com.cassianomenezes.mytransferwise.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;

import com.cassianomenezes.mytransferwise.BR;
import com.cassianomenezes.mytransferwise.adapter.ItemsListAdapter;
import com.cassianomenezes.mytransferwise.configuration.RecyclerConfiguration;
import com.cassianomenezes.mytransferwise.entries.FootballResponse;
import com.cassianomenezes.mytransferwise.entries.Player;
import com.cassianomenezes.mytransferwise.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseObservable {

    private ObservableField<List<Player>> itemsList = new ObservableField<>(new ArrayList<>());
    private ObservableBoolean running = new ObservableBoolean(false);
    private ObservableField<String> title = new ObservableField<>("");
    private ItemsListAdapter adapter;

    private Context context;
    private RecyclerConfiguration recyclerConfiguration;

    public MainViewModel(Context context) {
        this.context = context;
        setAdapter(new ItemsListAdapter(context, this, new ArrayList<>()));
        setupItemsList(new RecyclerConfiguration());
        getPlayers();
    }

    // --- region GETTERS & SETTERS ---


    public ObservableField<List<Player>> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Player> itemsList) {
        this.itemsList.set(itemsList);
        adapter.setList(itemsList);
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public ItemsListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ItemsListAdapter adapter) {
        this.adapter = adapter;
    }

    @Bindable
    public RecyclerConfiguration getRecyclerConfiguration() {
        this.recyclerConfiguration.setLayoutManager(new LinearLayoutManager(context));
        return recyclerConfiguration;
    }

    private void setupItemsList(RecyclerConfiguration recyclerConfiguration) {
        this.recyclerConfiguration = recyclerConfiguration;

        this.recyclerConfiguration.setLayoutManager(new LinearLayoutManager(context));
        this.recyclerConfiguration.setAdapter(getAdapter());
        notifyPropertyChanged(BR.recyclerConfiguration);
    }

    public ObservableBoolean getRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running.set(running);
    }

    // end region

    // region --- API CALLS ---


    private void getPlayers() {
        Call<FootballResponse> call = new RetrofitClient().getModel().getInfo();
        setRunning(true);
        call.enqueue(new Callback<FootballResponse>() {
            @Override
            public void onResponse(Call<FootballResponse> call, Response<FootballResponse> response) {
                FootballResponse footballResponse = response.body();
                handleSuccess(response.body());
                setRunning(false);
            }

            @Override
            public void onFailure(Call<FootballResponse> call, Throwable t) {
                System.out.print("aeaeae");
                setRunning(false);
            }
        });
    }

    private void handleSuccess(FootballResponse body) {
        setItemsList(body.getPlayerList());
    }

    // end region
}
