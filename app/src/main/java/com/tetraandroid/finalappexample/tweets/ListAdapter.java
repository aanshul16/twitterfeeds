package com.tetraandroid.finalappexample.tweets;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tetraandroid.retrofitexample.BR;
import com.tetraandroid.retrofitexample.R;
import com.tetraandroid.retrofitexample.databinding.TweetListRowBinding;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemViewHolder> {

    private List<ViewModel> list;
    private Context mContext;

    public ListAdapter(Context context, List<ViewModel> list) {
        this.list = list;
        mContext = context;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TweetListRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.tweet_list_row, parent, false);
        return new ListItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder holder, int position) {
        final ViewModel viewModel = list.get(position);
        holder.bindData(viewModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListItemViewHolder extends RecyclerView.ViewHolder {

        TweetListRowBinding binding;

        public ListItemViewHolder(TweetListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindData(Object obj) {
            binding.setVariable(BR.model, obj);
            binding.executePendingBindings();
        }
    }
}

