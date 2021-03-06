package com.cassianomenezes.mytransferwise.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.databinding.ActivityBeerBinding;
import com.cassianomenezes.mytransferwise.entries.BeerResponse;
import com.cassianomenezes.mytransferwise.util.Constants;
import com.cassianomenezes.mytransferwise.viewmodel.BeerViewModel;

import java.util.Objects;

public class BeerActivity extends AppCompatActivity {

    private ActivityBeerBinding binding;
    private BeerViewModel viewModel;
    private BeerResponse beerResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleBundle();
        setupViewModel();
        setupBinding();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    @SuppressLint("ShowToast")
    private void handleBundle() {
        if (getIntent().getExtras() != null && getIntent().getExtras().size() > 0) {
            beerResponse = getIntent().getExtras().getParcelable(Constants.BUNDLE_BEER_INFO);
        } else {
            Toast.makeText(this, getString(R.string.warning_internal_error), Toast.LENGTH_SHORT);
            finish();
        }
    }

    private void setupViewModel() {
        viewModel = new BeerViewModel(this, beerResponse);
    }

    private void setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_beer);
        binding.setViewModel(viewModel);
    }

    public ActivityBeerBinding getBinding() {
        return binding;
    }

    public void setBinding(ActivityBeerBinding binding) {
        this.binding = binding;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
