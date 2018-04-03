package com.cassianomenezes.mytransferwise.custom;

import android.support.v7.widget.RecyclerView;

import com.cassianomenezes.mytransferwise.databinding.ItemListBinding;

public class ItemsListHolder extends RecyclerView.ViewHolder {

    private ItemListBinding binding;

    public ItemsListHolder(ItemListBinding binding) {
        super(binding.getRoot());

        this.binding = binding;
    }

    public ItemListBinding getBinding() {
        return binding;
    }
}
