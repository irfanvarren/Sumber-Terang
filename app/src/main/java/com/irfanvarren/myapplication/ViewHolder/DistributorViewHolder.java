package com.irfanvarren.myapplication.ViewHolder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Adapter.DistributorListAdapter;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.R;

public class DistributorViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    private final LinearLayout DistributorView;
    private TextView txtDistributor,txtAddress;
    private Distributor current;
    DistributorListAdapter.OnDistributorListener onDistributorListener;

    private DistributorViewHolder(View itemView, DistributorListAdapter.OnDistributorListener onDistributorListener) {
        super(itemView);
        DistributorView = (LinearLayout) itemView.findViewById(R.id.distributor);
        txtDistributor = (TextView) itemView.findViewById(R.id.tvDistributor);
        txtAddress = (TextView) itemView.findViewById(R.id.tvAddress);
        this.onDistributorListener = onDistributorListener;
        itemView.setOnClickListener(this);
    }

    public void bind(Distributor distributor) {
        current = distributor;
        txtDistributor.setText(distributor.getName());
        if(TextUtils.isEmpty(distributor.getAddress())) {
            txtAddress.setVisibility(View.GONE);
        }else {
            txtAddress.setText(distributor.getAddress());
        }
    }

    public static DistributorViewHolder create(ViewGroup parent, DistributorListAdapter.OnDistributorListener onDistributorListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.distributor_lists, parent, false);
        return new DistributorViewHolder(view, onDistributorListener);
    }


    @Override
    public void onClick(View view) {
        onDistributorListener.OnDistributorClick(getAdapterPosition(), current);
    }
}