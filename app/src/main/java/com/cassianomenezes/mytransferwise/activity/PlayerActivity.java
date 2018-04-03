package com.cassianomenezes.mytransferwise.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.databinding.ActivityPlayerBinding;
import com.cassianomenezes.mytransferwise.entries.Player;
import com.cassianomenezes.mytransferwise.viewmodel.PlayerViewModel;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private PlayerViewModel viewModel;
    private Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
        setupBinding();
    }



    private void setupViewModel() {
        viewModel = new PlayerViewModel(this, player);
    }

    private void setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        binding.setViewModel(viewModel);
    }

    public ActivityPlayerBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityPlayerBinding binding) {
        this.binding = binding;
    }
}
