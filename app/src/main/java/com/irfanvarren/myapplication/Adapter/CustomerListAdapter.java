package com.irfanvarren.myapplication.Adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.ViewHolder.CustomerViewHolder;
import com.irfanvarren.myapplication.ViewHolder.CustomerViewHolder;

public class CustomerListAdapter extends ListAdapter<Customer, CustomerViewHolder> {
    private CustomerListAdapter.OnCustomerListener mOnCustomerListener;

    public CustomerListAdapter(@NonNull DiffUtil.ItemCallback<Customer> diffCallback, CustomerListAdapter.OnCustomerListener onCustomerListener) {
        super(diffCallback);
        this.mOnCustomerListener = onCustomerListener;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CustomerViewHolder.create(parent,mOnCustomerListener);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        Customer current = getItem(position);
        holder.bind(current);
    }

    public static class CustomerDiff extends DiffUtil.ItemCallback<Customer> {
        @Override
        public boolean areItemsTheSame(@NonNull Customer oldItem, @NonNull Customer newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Customer oldItem, @NonNull Customer newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnCustomerListener{
        void OnCustomerClick(int position, Customer current);
    }
}
