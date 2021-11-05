package com.irfanvarren.myapplication.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.R;
import com.irfanvarren.myapplication.ViewHolder.ProductViewHolder;

public class ProductListAdapter extends ListAdapter<ProductAndCategory, ProductViewHolder> {
    private RecyclerView mRecyclerView;
    private OnProductListener mOnProductListener;
    private int viewHolderType = 0;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;

    }

    @Override
    public void onViewAttachedToWindow( ProductViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public ProductListAdapter(@NonNull DiffUtil.ItemCallback<ProductAndCategory> diffCallback, OnProductListener onProductListener) {
        super(diffCallback);
        this.mOnProductListener = onProductListener;
    }

    public ProductListAdapter(@NonNull DiffUtil.ItemCallback<ProductAndCategory> diffCallback,OnProductListener onProductListener,int viewHolder) {
        super(diffCallback);
        this.mOnProductListener = onProductListener;
        this.viewHolderType = viewHolder;
    }

    @Override
    public int getItemViewType(int position){
        if(position == (getItemCount()-1)){
            return 1; // check if last item
        }
        return 0;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductViewHolder.create(parent,mOnProductListener,this.viewHolderType);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductAndCategory current = getItem(position);
        holder.bind(current,mRecyclerView);
    }

    public static class ProductDiff extends DiffUtil.ItemCallback<ProductAndCategory> {

        @Override
        public boolean areItemsTheSame(@NonNull ProductAndCategory oldItem, @NonNull ProductAndCategory newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductAndCategory oldItem, @NonNull ProductAndCategory newItem) {
            return oldItem.product.getName().equals(newItem.product.getName());
        }
    }

    public interface OnProductListener{
        void OnProductClick(int position, ProductAndCategory current);

        void OnAddClick(int position, ProductAndCategory current, View itemView);

        void OnSubstractClick(int position, ProductAndCategory current, View itemView);

    }


}
