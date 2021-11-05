package com.irfanvarren.myapplication.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.ViewHolder.DistributorViewHolder;
import com.irfanvarren.myapplication.ViewHolder.DistributorViewHolder;

public class DistributorListAdapter extends ListAdapter<Distributor, DistributorViewHolder> {
    private DistributorListAdapter.OnDistributorListener mOnDistributorListener;

    public DistributorListAdapter(@NonNull DiffUtil.ItemCallback<Distributor> diffCallback, DistributorListAdapter.OnDistributorListener onDistributorListener) {
        super(diffCallback);
        this.mOnDistributorListener = onDistributorListener;
    }

    @Override
    public DistributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return DistributorViewHolder.create(parent,mOnDistributorListener);
    }

    @Override
    public void onBindViewHolder(DistributorViewHolder holder, int position) {
        Distributor current = getItem(position);
        holder.bind(current);
    }

    public static class DistributorDiff extends DiffUtil.ItemCallback<Distributor> {
        @Override
        public boolean areItemsTheSame(@NonNull Distributor oldItem, @NonNull Distributor newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Distributor oldItem, @NonNull Distributor newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnDistributorListener{
        void OnDistributorClick(int position, Distributor current);
    }
}
