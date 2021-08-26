package com.irfanvarren.myapplication;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Model.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    private final LinearLayout productView;
    private TextView name;
    private TextView qty;
    private Product current;
    ProductListAdapter.OnProductListener onProductListener;

    private ProductViewHolder(View itemView, ProductListAdapter.OnProductListener onProductListener) {
        super(itemView);
        productView = (LinearLayout) itemView.findViewById(R.id.product);
        name = (TextView) itemView.findViewById(R.id.tvName);
        qty = (TextView) itemView.findViewById(R.id.tvQty);
        this.onProductListener = onProductListener;
        itemView.setOnClickListener(this);
    }

    public void bind(Product product) {
        current = product;
        name.setText(product.getName());
        qty.setText(String.valueOf(product.getQty()));

    }

    public static ProductViewHolder create(ViewGroup parent, ProductListAdapter.OnProductListener onProductListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.products_list, parent, false);
        return new ProductViewHolder(view,onProductListener);
    }


    @Override
    public void onClick(View view) {
        onProductListener.OnProductClick(getAdapterPosition(), current);
    }
}
