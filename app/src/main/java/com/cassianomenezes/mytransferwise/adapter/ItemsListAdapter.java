package com.cassianomenezes.mytransferwise.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cassianomenezes.mytransferwise.R;
import com.cassianomenezes.mytransferwise.custom.ItemsListHolder;
import com.cassianomenezes.mytransferwise.databinding.ItemListBinding;
import com.cassianomenezes.mytransferwise.entries.Beer;
import com.cassianomenezes.mytransferwise.viewmodel.MainViewModel;

import java.util.List;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListHolder> {

    private Context context;
    private MainViewModel viewModel;
    private List<Beer> itemsList;

    public ItemsListAdapter(Context context, MainViewModel viewModel, List<Beer> itemsList){
        this.context = context;
        this.viewModel = viewModel;
        this.itemsList = itemsList;
    }


    @NonNull
    @Override
    public ItemsListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_list, parent, false);
        binding.setViewModel(viewModel);
        return new ItemsListHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsListHolder holder, int position) {
        holder.getBinding().hItemListTvTitle.setText(itemsList.get(position).getName());
        holder.getBinding().hItemListLlTitle.setOnClickListener(v -> viewModel.gotoBeerActivity(position));

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setList(List<Beer> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }
}
