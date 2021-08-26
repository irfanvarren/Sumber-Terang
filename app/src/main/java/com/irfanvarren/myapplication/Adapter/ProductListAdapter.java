package com.irfanvarren.myapplication.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ProductViewHolder;

public class ProductListAdapter extends ListAdapter<Product, ProductViewHolder> {
    private OnProductListener mOnProductListener;
    public ProductListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback,OnProductListener onProductListener) {
        super(diffCallback);
        this.mOnProductListener = onProductListener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductViewHolder.create(parent,mOnProductListener);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product current = getItem(position);
        holder.bind(current);
    }

    public static class ProductDiff extends DiffUtil.ItemCallback<Product> {

        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnProductListener{
        void OnProductClick(int position, Product current);
    }

}
