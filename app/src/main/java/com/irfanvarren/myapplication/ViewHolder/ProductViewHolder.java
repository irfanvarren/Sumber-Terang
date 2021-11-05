package com.irfanvarren.myapplication.ViewHolder;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Locale;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static Context mContext;
    private ProductAndCategory current;
    private int viewHolderType = 0;
    public View holderView;
    ProductListAdapter.OnProductListener onProductListener;


    private ProductViewHolder(View itemView, ProductListAdapter.OnProductListener onProductListener, int viewHolderType) {
        super(itemView);
        this.holderView = itemView;
        this.onProductListener = onProductListener;
        this.viewHolderType = viewHolderType;
        itemView.setOnClickListener(this);
    }


    public void bind(ProductAndCategory product, RecyclerView recyclerView) {
        View itemView = this.itemView;
        LinearLayout root = (LinearLayout) itemView.findViewById(R.id.rootLayout);
        LinearLayout productView = (LinearLayout) itemView.findViewById(R.id.product);
        TextView name = (TextView) itemView.findViewById(R.id.tvName);
        TextView qty = (TextView) itemView.findViewById(R.id.tvQty);
        TextView category = (TextView) itemView.findViewById(R.id.tvCategory);
        TextView price = (TextView) itemView.findViewById(R.id.tvPrice);
        ImageView img = (ImageView) itemView.findViewById(R.id.img);
        ImageButton addQty = (ImageButton) itemView.findViewById(R.id.addBtn);
        ImageButton substractQty = (ImageButton) itemView.findViewById(R.id.substractBtn);

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        String qtyText = String.valueOf(product.product.getQty());

        if (this.viewHolderType == 0) {
            qty.setTextColor(itemView.getResources().getColor(R.color.black));
            qty.setBackgroundResource(0);
            qtyText = "qty: " + qtyText;
            price.setText("Rp. " + nf.format(product.product.getPrice()));
            price.setVisibility(View.VISIBLE);
        } else {
            qtyText = "0";

            if (product.cartQty != null && product.cartPrice != null) {
                if (product.cartQty > 0) {
                    qty.setTextColor(itemView.getResources().getColor(R.color.black));
                    qty.setBackgroundResource(0);
                    qtyText = String.valueOf(product.cartQty);
                    addQty.setVisibility(View.VISIBLE);
                    substractQty.setVisibility(View.VISIBLE);
                }

                if (product.cartPrice > 0) {
                    price.setText("Rp. " + nf.format(product.cartPrice));
                    price.setVisibility(View.VISIBLE);
                }

                if(this.viewHolderType == 2 && product.cartPrice <= 0){
                    TextView remainingStock = itemView.findViewById(R.id.remainingStock);
                    remainingStock.setVisibility(View.VISIBLE);
                    remainingStock.setText("Sisa Stok : "+ String.valueOf(product.product.getQty()));

                    price.setText("Rp. " + nf.format(product.product.getPrice()));
                    price.setVisibility(View.VISIBLE);
                }
            }
        }

        qty.setText(qtyText);

        if (getItemViewType() == 1) {
//            if(recyclerView.canScrollVertically(-1)) {
//                ViewGroup.MarginLayoutParams rootLayoutParams = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
//                rootLayoutParams.setMargins(0 ,0,0,0);
//                root.requestLayout();
//
//                LinearLayout recyclerViewEnd = new LinearLayout(mContext);
//                float dip = 90f;
//                Resources r = root.getResources();
//                int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
//                final ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, px);
//                recyclerViewEnd.setLayoutParams(params);
//                recyclerViewEnd.setClickable(false);
//                recyclerViewEnd.setBackgroundColor(Color.parseColor("#F6F6F6"));
//                recyclerViewEnd.setOrientation(LinearLayout.HORIZONTAL);
//                if(root.indexOfChild(recyclerViewEnd) == -1){
//                    root.addView(recyclerViewEnd);
//                }
            // }else{
            float dip = 90f;
            Resources r = root.getResources();
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());

            ViewGroup.MarginLayoutParams rootLayoutParams = (ViewGroup.MarginLayoutParams) root.getLayoutParams();
            rootLayoutParams.setMargins(0, 0, 0, px);
            root.requestLayout();
            // }
        }
        current = product;
        name.setText(product.product.getName());

        if (product.category != null) {
            category.setText(product.category.getName());
        } else {
            category.setText("-");
        }
        if (!TextUtils.isEmpty(product.product.getImagePath())) {
            try {
                ContextWrapper cw = new ContextWrapper(mContext);
                File directory = cw.getDir("products", Context.MODE_PRIVATE);
                File f = new File(directory, product.product.getImagePath());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                img.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            img.setImageResource(R.drawable.ic_baseline_image_not_supported_24);
        }


        if (addQty != null) {
            addQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProductListener.OnAddClick(getAdapterPosition(), current, itemView);
                }
            });
        }
        if (substractQty != null) {
            substractQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onProductListener.OnSubstractClick(getAdapterPosition(), current, itemView);
                }
            });
        }
    }

    public static ProductViewHolder create(ViewGroup parent, ProductListAdapter.OnProductListener onProductListener, int viewHolderType) {
        mContext = parent.getContext();
        View view;
        switch (viewHolderType) {
            case 1: //buy / sell
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.products_cart_list, parent, false);
                break;
            default: // default 0, product list
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.products_list, parent, false);
                break;
        }


        return new ProductViewHolder(view, onProductListener, viewHolderType);
    }


    @Override
    public void onClick(View view) {
        onProductListener.OnProductClick(getAdapterPosition(), current);
    }
}
