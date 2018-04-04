package com.cassianomenezes.mytransferwise.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.cassianomenezes.mytransferwise.BR;
import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.activity.PlayerActivity;
import com.cassianomenezes.mytransferwise.adapter.ItemsListAdapter;
import com.cassianomenezes.mytransferwise.configuration.RecyclerConfiguration;
import com.cassianomenezes.mytransferwise.configuration.SwipeRefreshConfiguration;
import com.cassianomenezes.mytransferwise.database.SQLiteDatabaseHandler;
import com.cassianomenezes.mytransferwise.entries.FootballResponse;
import com.cassianomenezes.mytransferwise.entries.Player;
import com.cassianomenezes.mytransferwise.network.RetrofitClient;
import com.cassianomenezes.mytransferwise.util.Constants;
import com.cassianomenezes.mytransferwise.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseObservable implements SwipeRefreshLayout.OnRefreshListener{

    private ObservableField<List<Player>> itemsList = new ObservableField<>(new ArrayList<>());
    private ObservableBoolean running = new ObservableBoolean(false);
    private ObservableField<String> title = new ObservableField<>("");
    private ObservableBoolean noDataAvailable = new ObservableBoolean(false);
    private ObservableBoolean isSwipeToRefreshRunning = new ObservableBoolean(false);
    private ObservableBoolean isDataSaved = new ObservableBoolean(false);
    private ObservableBoolean isOffline = new ObservableBoolean(false);

    private ItemsListAdapter adapter;
    private Context context;
    private RecyclerConfiguration recyclerConfiguration;
    private SwipeRefreshConfiguration swipeRefreshConfiguration;
    private SQLiteDatabaseHandler db;

    public MainViewModel(Context context) {
        this.context = context;
        setup();
    }

    private void setup() {
        setAdapter(new ItemsListAdapter(context, this, new ArrayList<>()));
        setupItemsList(new RecyclerConfiguration());
        db = new SQLiteDatabaseHandler(context);

        swipeRefreshConfiguration = new SwipeRefreshConfiguration();
        swipeRefreshConfiguration.setOnRefreshListener(this);

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

    public ObservableBoolean getNoDataAvailable() {
        return noDataAvailable;
    }

    public void setNoDataAvailable(boolean noDataAvailable) {
        this.noDataAvailable.set(noDataAvailable);
    }

    @Bindable
    public SwipeRefreshConfiguration getSwipeRefreshConfiguration() {
        return swipeRefreshConfiguration;
    }

    public void setSwipeRefreshEnable(boolean enable) {
        swipeRefreshConfiguration.setEnable(enable);
        notifyPropertyChanged(BR.swipeRefreshConfiguration);
    }

    public ObservableBoolean getIsSwipeToRefreshRunning() {
        return isSwipeToRefreshRunning;
    }

    public void setIsSwipeToRefreshRunning(boolean isSwipeToRefreshRunning) {
        this.isSwipeToRefreshRunning.set(isSwipeToRefreshRunning);
    }

    public ObservableBoolean getIsDataSaved() {
        return isDataSaved;
    }

    public void setIsDataSaved(boolean isDataSaved) {
        this.isDataSaved.set(isDataSaved);
    }

    public ObservableBoolean getIsOffline() {
        return isOffline;
    }

    public void setIsOffline(boolean isOffline) {
        this.isOffline.set(isOffline);
    }

    // end region

    // region --- API CALLS ---

    private void getPlayers() {
        if (RequestUtil.hasInternetConnection(context)) {
            setIsOffline(false);
            Call<FootballResponse> call = new RetrofitClient().getModel().getPlayersInfo();
            setRunning(true);
            call.enqueue(new Callback<FootballResponse>() {
                @Override
                public void onResponse(Call<FootballResponse> call, Response<FootballResponse> response) {
                    if(response.isSuccessful()) {
                        handleSuccess(response.body());
                    } else {
                        showAlertDialog(context.getString(R.string.warning_data_fetch_error_title),
                                context.getString(R.string.warning_data_fetch_error_message));
                    }
                }

                @Override
                public void onFailure(Call<FootballResponse> call, Throwable t) {
                    showAlertDialog(context.getString(R.string.warning_data_fetch_error_title),
                            context.getString(R.string.warning_data_fetch_error_message));
                    setRunning(false);
                    setIsSwipeToRefreshRunning(running.get());
                }
            });
        } else {
            handleNoInternet();
        }

    }

    // end region

    // region --- DATA HANDLING ---

    private void handleSuccess(FootballResponse footballResponse) {
        setItemsList(footballResponse.getPlayerList());
        setRunning(false);
        setSwipeRefreshEnable(!running.get());
        setNoDataAvailable(false);
        setIsSwipeToRefreshRunning(running.get());
    }

    private void handleNoInternet() {
        showAlertDialog(context.getString(R.string.warning_no_internet_connection_title),
                context.getString(R.string.warning_no_internet_connection_message));
        setRunning(false);
        setIsOffline(true);

        setItemsList(db.getAllPlayers());
        if (itemsList.get().isEmpty()) {
            setNoDataAvailable(true);
        } else {
            setIsDataSaved(true);
        }
    }

    // end region

    // region --- NAVIGATION ---

    public void gotoPlayerActivity(int position) {
        Player player = itemsList.get().get(position);
        Intent intent = new Intent(context, PlayerActivity.class);
        intent.putExtra(Constants.BUNDLE_PLAYER_INFO, player);
        context.startActivity(intent);

        if (db.getPlayer(player.getName()) == null) {
            db.addPlayer(player);
        } else {
            db.updatePlayer(player);
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setNeutralButton(context.getString(R.string.try_again), (dialog, which) -> getPlayers())
                .show();
    }

    // end region


    // region --- LISTENERS ---
    @Override
    public void onRefresh() {
        if (RequestUtil.hasInternetConnection(context)) {
            setIsSwipeToRefreshRunning(true);
            getPlayers();
        } else {
            showAlertDialog(context.getString(R.string.warning_no_internet_connection_title),
                    context.getString(R.string.warning_no_internet_connection_message));
            setIsSwipeToRefreshRunning(false);
            setRunning(false);
        }
    }

    // end region
}
