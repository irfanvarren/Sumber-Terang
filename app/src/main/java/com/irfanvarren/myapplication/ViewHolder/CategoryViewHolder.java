package com.irfanvarren.myapplication.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Adapter.CategoryListAdapter;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    private final LinearLayout CategoryView;
    private TextView txtCategory;
    private Category current;
    CategoryListAdapter.OnCategoryListener onCategoryListener;

    private CategoryViewHolder(View itemView, CategoryListAdapter.OnCategoryListener onCategoryListener) {
        super(itemView);
        CategoryView = (LinearLayout) itemView.findViewById(R.id.category);
        txtCategory = (TextView) itemView.findViewById(R.id.tvCategory);
        this.onCategoryListener = onCategoryListener;
        itemView.setOnClickListener(this);
    }

    public void bind(Category category) {
        current = category;
        txtCategory.setText(category.getName());
    }

    public static CategoryViewHolder create(ViewGroup parent, CategoryListAdapter.OnCategoryListener onCategoryListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categories_list, parent, false);
        return new CategoryViewHolder(view, onCategoryListener);
    }


    @Override
    public void onClick(View view) {
        onCategoryListener.OnCategoryClick(getAdapterPosition(), current);
    }
}