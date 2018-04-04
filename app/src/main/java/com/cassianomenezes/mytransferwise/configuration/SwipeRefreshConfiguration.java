package com.cassianomenezes.mytransferwise.configuration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;

import com.cassianomenezes.mytransferwise.BR;

public class SwipeRefreshConfiguration  extends BaseObservable {

    private boolean enable = true;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

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
