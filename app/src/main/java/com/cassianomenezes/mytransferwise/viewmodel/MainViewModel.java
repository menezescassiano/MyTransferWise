package com.cassianomenezes.mytransferwise.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;

import com.cassianomenezes.mytransferwise.BR;
import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.activity.BeerActivity;
import com.cassianomenezes.mytransferwise.activity.MainActivity;
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
            call.enqueue(new Callback<List<Beer>>() {
                @Override
                public void onResponse(@NonNull Call<List<Beer>> call, @NonNull Response<List<Beer>> response) {
                    if(response.isSuccessful()) {
                        setRunning(false);
                        handleSuccess(response.body());
                    } else {
                        showAlertDialog(context.getString(R.string.warning_data_fetch_error_title),
                                context.getString(R.string.warning_data_fetch_error_message));
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Beer>> call, @NonNull Throwable t) {
                    setRunning(false);
                    showAlertDialog(context.getString(R.string.warning_data_fetch_error_title),
                            context.getString(R.string.warning_data_fetch_error_message));
                    setIsSwipeToRefreshRunning(running.get());
                }
            });
        } else {
            handleNoInternetAccess();
        }

    }

    // end region

    // region --- DATA HANDLING ---

    private void handleSuccess(List<Beer> beerList) {
        setBeerItemsList(beerList);
        setSwipeRefreshEnable(!running.get());
        setNoDataAvailable(false);
        setIsSwipeToRefreshRunning(running.get());
    }

    private void handleNoInternetAccess() {
        showAlertDialog(context.getString(R.string.warning_no_internet_connection_title),
                context.getString(R.string.warning_no_internet_connection_message));
        setRunning(false);
        setIsOffline(true);

        setBeerItemsList(db.getAllBeers());
        if (beerItemsList.get().isEmpty()) {
            setNoDataAvailable(true);
        } else {
            setNoDataAvailable(false);
        }
    }

    // end region

    // region --- NAVIGATION ---

    public void goToBeerActivity(int position) {
        Beer beer = beerItemsList.get().get(position);
        Intent intent = new Intent(context, BeerActivity.class);
        intent.putExtra(Constants.BUNDLE_BEER_INFO, beer);
        context.startActivity(intent);

        //When the user selects a beer, it will automatically be added/updated into the database
        if (db.getBeer(beer.getName()) == null) {
            db.addBeer(beer);
        } else {
            db.updateBeer(beer);
        }
    }

    private void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    setSwipeRefreshEnable(true);
                    dialog.dismiss();
                    //needed to do this in order to refresh layout
                    ((MainActivity)context).getBinding().invalidateAll();
                })
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
            setSwipeRefreshEnable(false);
            setRunning(false);
        }

    }

    // end region
}
