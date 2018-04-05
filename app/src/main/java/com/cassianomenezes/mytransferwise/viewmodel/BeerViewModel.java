package com.cassianomenezes.mytransferwise.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.entries.Beer;

public class BeerViewModel {

    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> tagline = new ObservableField<>();
    private ObservableField<String> firstBrewed = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();
    private ObservableField<String> imageUrl = new ObservableField<>();

    private Context context;
    private Beer beer;

    public BeerViewModel(Context context, Beer beer) {
        this.context = context;
        this.beer = beer;
        handleBeerData();
    }

    // --- region GETTERS & SETTERS ---

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableField<String> getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline.set(tagline);
    }

    public ObservableField<String> getFirstBrewed() {
        return firstBrewed;
    }

    public void setFirstBrewed(String firstBrewed) {
        this.firstBrewed.set(firstBrewed);
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ObservableField<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl.set(imageUrl);
    }

    // end region

    // --- region DATA HANDLING ---


    private void handleBeerData() {
        setName(String.format(context.getString(R.string.name), beer.getName()));
        setTagline(String.format(context.getString(R.string.tag_line), beer.getTagline()));
        setFirstBrewed(String.format(context.getString(R.string.first_brewed), beer.getFirstBrewed()));
        setDescription(String.format(context.getString(R.string.description), beer.getDescription()));
        setImageUrl(beer.getImageUrl());
    }

    // end region
}
