package com.irfanvarren.myapplication.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Adapter.InventoryListAdapter;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class InventoryViewHolder extends RecyclerView.ViewHolder  {
    private final LinearLayout InventoryView;
    private TextView txtQty,txtPrice,txtPriceDesc,txtDate;
    private Inventory current;
    private ImageButton editBtn, deleteBtn;
    private ImageView imgType;
    InventoryListAdapter.OnInventoryListener onInventoryListener;

    private InventoryViewHolder(View itemView, InventoryListAdapter.OnInventoryListener onInventoryListener) {
        super(itemView);
        InventoryView = (LinearLayout) itemView.findViewById(R.id.inventory);
        txtQty = (TextView) itemView.findViewById(R.id.qty);
        txtPrice = (TextView) itemView.findViewById(R.id.price);
        txtPriceDesc = (TextView) itemView.findViewById(R.id.priceDesc);
        txtDate = (TextView) itemView.findViewById(R.id.date);
        editBtn = (ImageButton) itemView.findViewById(R.id.editBtn);
        deleteBtn = (ImageButton) itemView.findViewById(R.id.deleteBtn);
        imgType = (ImageView) itemView.findViewById(R.id.typeImg);
        this.onInventoryListener = onInventoryListener;

    }

    public void bind(Inventory inventory) {
        current = inventory;
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        txtPrice.setText("Rp. " + nf.format(current.getPrice()));
        txtQty.setText(String.valueOf(current.getQty()));

        String strDate = new SimpleDateFormat("dd/MM/yyyy").format(current.getCreatedAt());
        txtDate.setText(strDate);

        if(current.getType().equals("in")){
            imgType.setImageResource(R.drawable.ic_baseline_arrow_downward_green_24);
            txtPriceDesc.setText("Harga Beli");
        }else{
            imgType.setImageResource(R.drawable.ic_baseline_arrow_upward_red_24);
            txtPriceDesc.setText("Harga Jual");
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInventoryListener.OnEditClick(getAdapterPosition(),current);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInventoryListener.OnDeleteClick(getAdapterPosition(),current);
            }
        });
    }

    public static InventoryViewHolder create(ViewGroup parent, InventoryListAdapter.OnInventoryListener onInventoryListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_history_list, parent, false);
        return new InventoryViewHolder(view, onInventoryListener);
    }

}