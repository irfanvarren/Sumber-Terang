package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfanvarren.myapplication.Adapter.CategoryListAdapter;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.ViewModel.CategoryViewModel;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryListAdapter.OnCategoryListener{
    private CategoryViewModel mCategoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final CategoryListAdapter adapter = new CategoryListAdapter(new CategoryListAdapter.CategoryDiff(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAll().observe(this, products -> {
            adapter.submitList(products);
    });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, CategoryInputActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void OnCategoryClick(int position, Category current) {
        Intent intent = new Intent(CategoryActivity.this, CategoryInputActivity.class);
        intent.putExtra("category",current);
        intent.putExtra("action","edit");
        startActivity(intent);
    }
}