package com.irfanvarren.myapplication.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.R;
import com.irfanvarren.myapplication.ViewHolder.InventoryViewHolder;

public class InventoryListAdapter extends ListAdapter<Inventory, InventoryViewHolder> {
    private OnInventoryListener mOnInventoryListener;
    private int viewHolderType = 0;

    public InventoryListAdapter(@NonNull DiffUtil.ItemCallback<Inventory> diffCallback,OnInventoryListener onInventoryListener) {
        super(diffCallback);
        this.mOnInventoryListener = onInventoryListener;
    }

    public InventoryListAdapter(@NonNull DiffUtil.ItemCallback<Inventory> diffCallback,OnInventoryListener onInventoryListener,int viewHolder) {
        super(diffCallback);
        this.mOnInventoryListener = onInventoryListener;
        this.viewHolderType = viewHolder;
    }

    @Override
    public InventoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return InventoryViewHolder.create(parent,mOnInventoryListener);
    }

    @Override
    public void onBindViewHolder(InventoryViewHolder holder, int position) {
        Inventory current = getItem(position);
        holder.bind(current);
    }

    public static class InventoryDiff extends DiffUtil.ItemCallback<Inventory> {

        @Override
        public boolean areItemsTheSame(@NonNull Inventory oldItem, @NonNull Inventory newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Inventory oldItem, @NonNull Inventory newItem) {
            return oldItem.getProductName().equals(newItem.getProductName());
        }
    }

    public interface OnInventoryListener{

        void OnEditClick(int position, Inventory current);

        void OnDeleteClick(int position, Inventory current);

    }


}
