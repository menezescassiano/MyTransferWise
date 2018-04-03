package com.cassianomenezes.mytransferwise.configuration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;

import com.cassianomenezes.mytransferwise.BR;
import com.cassianomenezes.mytransferwise.custom.SpacesItemDecoration;

public class RecyclerConfiguration extends BaseObservable {

    private RecyclerView.ItemDecoration itemDecoration = new SpacesItemDecoration(0);
    private RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
    private RecyclerView.OnScrollListener onScrollListener;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Bindable
    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        notifyPropertyChanged(BR.layoutManager);
    }

    @Bindable
    public RecyclerView.ItemAnimator getItemAnimator() {
        return itemAnimator;
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
        this.itemAnimator = itemAnimator;
        notifyPropertyChanged(BR.itemAnimator);
    }

    @Bindable
    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
        notifyPropertyChanged(BR.itemDecoration);
    }

    @Bindable
    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        notifyPropertyChanged(BR.adapter);
    }

    @Bindable
    public RecyclerView.OnScrollListener getOnScrollListener() {
        return onScrollListener;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        notifyPropertyChanged(BR.onScrollListener);
    }
}
