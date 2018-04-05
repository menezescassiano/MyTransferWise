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
import com.cassianomenezes.mytransferwise.activity.BeerActivity;
import com.cassianomenezes.mytransferwise.adapter.ItemsListAdapter;
import com.cassianomenezes.mytransferwise.configuration.RecyclerConfiguration;
import com.cassianomenezes.mytransferwise.configuration.SwipeRefreshConfiguration;
import com.cassianomenezes.mytransferwise.database.SQLiteDatabaseHandler;
import com.cassianomenezes.mytransferwise.entries.Beer;
import com.cassianomenezes.mytransferwise.network.RetrofitClient;
import com.cassianomenezes.mytransferwise.util.Constants;
import com.cassianomenezes.mytransferwise.util.RequestUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseObservable implements SwipeRefreshLayout.OnRefreshListener{

    private ObservableField<List<Beer>> beerItemsList = new ObservableField<>(new ArrayList<>());
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

        getBeers();
    }

    // --- region GETTERS & SETTERS ---

    /*public ObservableField<List<Player>> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<Player> itemsList) {
        this.itemsList.set(itemsList);
        adapter.setList(itemsList);
    }*/

    public ObservableField<List<Beer>> getBeerItemsList() {
        return beerItemsList;
    }

    public void setBeerItemsList(List<Beer> beerItemsList) {
        this.beerItemsList.set(beerItemsList);
        adapter.setList(beerItemsList);
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

    private void getBeers() {
        if (RequestUtil.hasInternetConnection(context)) {
            setIsOffline(false);
            setRunning(true);
            Call<List<Beer>> call = RetrofitClient.getInstance().getModel().getBeerInfo();
            setRunning(true);
            call.enqueue(new Callback<List<Beer>>() {
                @Override
                public void onResponse(Call<List<Beer>> call, Response<List<Beer>> response) {
                    if(response.isSuccessful()) {
                        handleSuccess(response.body());
                    } else {
                        showAlertDialog(context.getString(R.string.warning_data_fetch_error_title),
                                context.getString(R.string.warning_data_fetch_error_message));
                    }
                }

                @Override
                public void onFailure(Call<List<Beer>> call, Throwable t) {
                    showAlertDialog(context.getString(R.string.warning_data_fetch_error_title),
                            context.getString(R.string.warning_data_fetch_error_message));
                    setRunning(false);
                    setIsSwipeToRefreshRunning(running.get());
                }
            });
        }

    }

    // end region

    // region --- DATA HANDLING ---

    private void handleSuccess(List<Beer> beerList) {
        setBeerItemsList(beerList);
        setRunning(false);
        setSwipeRefreshEnable(!running.get());
        setNoDataAvailable(false);
        setIsSwipeToRefreshRunning(running.get());
    }

    /*private void handleNoInternet() {
        showAlertDialog(context.getString(R.string.warning_no_internet_connection_title),
                context.getString(R.string.warning_no_internet_connection_message));
        setRunning(false);
        setIsOffline(true);

        setBeerItemsList(db.getAllPlayers());
        if (beerItemsList.get().isEmpty()) {
            setNoDataAvailable(true);
        } else {
            setIsDataSaved(true);
        }
    }*/

    // end region

    // region --- NAVIGATION ---

    public void gotoBeerActivity(int position) {
        Beer beer = beerItemsList.get().get(position);
        Intent intent = new Intent(context, BeerActivity.class);
        intent.putExtra(Constants.BUNDLE_BEER_INFO, beer);
        context.startActivity(intent);

        /*if (db.getPlayer(player.getName()) == null) {
            db.addPlayer(player);
        } else {
            db.updatePlayer(player);
        }*/
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                .setNeutralButton(context.getString(R.string.try_again), (dialog, which) -> getBeers())
                .show();
    }

    // end region


    // region --- LISTENERS ---
    @Override
    public void onRefresh() {
        if (RequestUtil.hasInternetConnection(context)) {
            setIsSwipeToRefreshRunning(true);
            getBeers();
        } else {
            showAlertDialog(context.getString(R.string.warning_no_internet_connection_title),
                    context.getString(R.string.warning_no_internet_connection_message));
            setIsSwipeToRefreshRunning(false);
            setRunning(false);
        }
    }

    // end region
}
