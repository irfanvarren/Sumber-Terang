package com.irfanvarren.myapplication.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.ViewHolder.CategoryViewHolder;

public class CategoryListAdapter extends ListAdapter<Category, CategoryViewHolder> {
    private OnCategoryListener mOnCategoryListener;

    public CategoryListAdapter(@NonNull DiffUtil.ItemCallback<Category> diffCallback,OnCategoryListener onCategoryListener) {
        super(diffCallback);
        this.mOnCategoryListener = onCategoryListener;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CategoryViewHolder.create(parent,mOnCategoryListener);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category current = getItem(position);
        holder.bind(current);
    }

    public static class CategoryDiff extends DiffUtil.ItemCallback<Category> {
        @Override
        public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnCategoryListener{
        void OnCategoryClick(int position, Category current);
    }

}
