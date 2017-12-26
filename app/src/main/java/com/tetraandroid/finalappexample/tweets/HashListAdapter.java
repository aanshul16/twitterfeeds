package com.tetraandroid.finalappexample.tweets;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetraandroid.retrofitexample.BR;
import com.tetraandroid.retrofitexample.R;
import com.tetraandroid.retrofitexample.databinding.HashListRowBinding;

import java.util.List;

public class HashListAdapter extends RecyclerView.Adapter<HashListAdapter.ListItemViewHolder> implements View.OnClickListener {

    private List<HashViewModel> list;
    private Context mContext;
    private HashTagClick hashTagClick;

    public HashListAdapter(HashTagClick hashTagClick, Context context, List<HashViewModel> list) {
        this.hashTagClick = hashTagClick;
        this.list = list;
        mContext = context;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HashListRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.hash_list_row, parent, false);
        return new ListItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        final HashViewModel viewModel = list.get(position);
        holder.binding.textViewFragmentlistTaskName.setOnClickListener(this);
        holder.bindData(viewModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        hashTagClick.hashTagClickItem(((TextView) v).getText().toString());
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        HashListRowBinding binding;

        public ListItemViewHolder(HashListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }

    public interface HashTagClick {
        public void hashTagClickItem(String str);
    }
}

