package com.irfanvarren.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.R;

import java.util.List;

public class CategoryArrayAdapter extends ArrayAdapter<Category> {
    private List<Category> categories;
    private Context mContext;
    private int itemLayout;
    public CategoryArrayAdapter(@NonNull Context context,int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        mContext = context;
        categories = objects;
        itemLayout = resource;
    }

    @Override
    public int getCount(){
        return categories.size();
    }

    @Override
    public Category getItem(int position){
        categories.get(position);
        return categories.get(position);
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

