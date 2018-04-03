package com.cassianomenezes.mytransferwise.util;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.cassianomenezes.mytransferwise.configuration.RecyclerConfiguration;

public class BindAdapterUtil {

    @BindingAdapter({"configuration"})
    public static void configureRecyclerView(RecyclerView recyclerView, RecyclerConfiguration configuration) {
        recyclerView.addItemDecoration(configuration.getItemDecoration());
        recyclerView.setLayoutManager(configuration.getLayoutManager());
        recyclerView.setItemAnimator(configuration.getItemAnimator());
        recyclerView.setAdapter(configuration.getAdapter());
        if (configuration.getOnScrollListener() != null) {
            recyclerView.addOnScrollListener(configuration.getOnScrollListener());
        }
    }

}
