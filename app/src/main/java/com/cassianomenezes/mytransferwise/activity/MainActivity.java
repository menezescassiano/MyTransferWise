package com.cassianomenezes.mytransferwise.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.databinding.ActivityMainBinding;
import com.cassianomenezes.mytransferwise.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewModel();
        setupBinding();
        viewModel.setSwipeRefreshEnable(!viewModel.getRunning().get());
    }

    private void setupViewModel() {
        viewModel = new MainViewModel(this);
    }

    private void setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);
    }

    public ActivityMainBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityMainBinding binding) {
        this.binding = binding;
    }

}
