package com.irfanvarren.myapplication.Adapter;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PurchaseItemListAdapter extends ListAdapter<CartItem,PurchaseItemListAdapter.ViewHolder> {
    private Context mContext;
    private CartItemListener mCartItemListener;

    public PurchaseItemListAdapter(DiffUtil.ItemCallback<CartItem> diffCallback, CartItemListener cartItemListener) {
        super(diffCallback);
        this.mCartItemListener = cartItemListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context mContext;
        private TextView txtCartItemName,txtCartItemDesc;
        private ShapeableImageView imgCartItem;
        private View rootView;
        private CartItem mCartItem;
        private CartItemListener mCartItemListener;
        public ViewHolder(View view,CartItemListener cartItemListener){
            super(view);
            mCartItemListener = cartItemListener;
            mContext = view.getContext();
            txtCartItemName = view.findViewById(R.id.productName);
            txtCartItemDesc = view.findViewById(R.id.productDesc);
            imgCartItem = view.findViewById(R.id.productImg);
            rootView = view;
        }
        public void bind(CartItem cartItem){
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));

            mCartItem = cartItem;

            if(!TextUtils.isEmpty(cartItem.getName())) {
                txtCartItemName.setText(cartItem.getName());
            }

            int qty = cartItem.getQty();
            Double price = cartItem.getPrice();
            String desc = String.valueOf(qty) + " x " + "Rp. " + nf.format(price) + " = Rp. "+ nf.format(qty*price);
            txtCartItemDesc.setText(desc);


            if (!TextUtils.isEmpty(cartItem.product.getImagePath())) {
                try {
                    ContextWrapper cw = new ContextWrapper(mContext);
                    File directory = cw.getDir("products", Context.MODE_PRIVATE);
                    File f = new File(directory, cartItem.product.getImagePath());
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    imgCartItem.setImageBitmap(b);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                imgCartItem.setImageResource(R.drawable.ic_baseline_image_not_supported_24);
            }

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCartItemListener.CartItemClick(getAdapterPosition(),mCartItem);
                }
            });
        }

        @Override
        public void onClick(View view) {
        mCartItemListener.CartItemClick(getAdapterPosition(),mCartItem);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_list,parent,false);
        return new ViewHolder(view,mCartItemListener);
    }

    @Override
    public void onBindViewHolder(PurchaseItemListAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class ItemDiff extends DiffUtil.ItemCallback<CartItem> {

        @Override
        public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
            return oldItem.getProductId() == newItem.getProductId();
        }
    }


    public interface CartItemListener{
        public void CartItemClick(Integer position,CartItem cartItem);
    }

}
