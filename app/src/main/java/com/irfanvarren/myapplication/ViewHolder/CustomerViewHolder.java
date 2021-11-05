package com.irfanvarren.myapplication.ViewHolder;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Adapter.CustomerListAdapter;
import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.R;

public class CustomerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
    private final LinearLayout CustomerView;
    private TextView txtCustomer,txtAddress;
    private Customer current;
    CustomerListAdapter.OnCustomerListener onCustomerListener;

    private CustomerViewHolder(View itemView, CustomerListAdapter.OnCustomerListener onCustomerListener) {
        super(itemView);
        CustomerView = (LinearLayout) itemView.findViewById(R.id.customer);
        txtCustomer = (TextView) itemView.findViewById(R.id.tvCustomer);
        txtAddress = (TextView) itemView.findViewById(R.id.tvAddress);
        this.onCustomerListener = onCustomerListener;
        itemView.setOnClickListener(this);
    }

    public void bind(Customer customer) {
        current = customer;
        txtCustomer.setText(customer.getName());
        if(TextUtils.isEmpty(customer.getAddress())) {
            txtAddress.setVisibility(View.GONE);
        }else {
            txtAddress.setText(customer.getAddress());
        }
    }

    public static CustomerViewHolder create(ViewGroup parent, CustomerListAdapter.OnCustomerListener onCustomerListener) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_lists, parent, false);
        return new CustomerViewHolder(view, onCustomerListener);
    }


    @Override
    public void onClick(View view) {
        onCustomerListener.OnCustomerClick(getAdapterPosition(), current);
    }
}