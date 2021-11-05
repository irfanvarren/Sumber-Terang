package com.irfanvarren.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.R;

import java.util.List;

public class DistributorArrayAdapter extends ArrayAdapter<Distributor> {
    private List<Distributor> distributors;
    private Context mContext;
    private int itemLayout;
    public DistributorArrayAdapter(@NonNull Context context,int resource, @NonNull List<Distributor> objects) {
        super(context, resource, objects);
        mContext = context;
        distributors = objects;
        itemLayout = resource;
    }

    @Override
    public int getCount(){
        return distributors.size();
    }

    @Override
    public Distributor getItem(int position){
        distributors.get(position);
        return distributors.get(position);
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

