package com.cassianomenezes.mytransferwise.configuration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.cassianomenezes.mytransferwise.BR;

public class SwipeRefreshConfiguration  extends BaseObservable {

    private int size = SwipeRefreshLayout.DEFAULT;
    private boolean enable = true;
    private int[] colorScheme = {0, 0, 0, 0};
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    @Bindable
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        notifyPropertyChanged(BR.size);
    }

    @Bindable
    public int[] getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(int... colorScheme) {
        this.colorScheme = colorScheme;
        notifyPropertyChanged(BR.colorScheme);
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        notifyPropertyChanged(BR.enable);
    }

    @Bindable
    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return onRefreshListener;
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        notifyPropertyChanged(BR.onRefreshListener);
    }
}
