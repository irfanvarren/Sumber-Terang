package com.irfanvarren.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.R;

import java.util.List;

public class CustomerArrayAdapter extends ArrayAdapter<Customer> {
    private List<Customer> customers;
    private Context mContext;
    private int itemLayout;
    public CustomerArrayAdapter(@NonNull Context context,int resource, @NonNull List<Customer> objects) {
        super(context, resource, objects);
        mContext = context;
        customers = objects;
        itemLayout = resource;
    }

    @Override
    public int getCount(){
        return customers.size();
    }

    @Override
    public Customer getItem(int position){
        customers.get(position);
        return customers.get(position);
    }

    @Override
    public View getView(int position, View view , @NonNull ViewGroup parent){
        if(view == null){
            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout,parent,false);
        }
        TextView strName = (TextView) view.findViewById(R.id.textView);
        strName.setText(getItem(position).getName());
        return view;
    }

}

