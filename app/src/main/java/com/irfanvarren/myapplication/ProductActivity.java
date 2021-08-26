package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

public class ProductActivity extends AppCompatActivity implements ProductListAdapter.OnProductListener{
    private ProductViewModel mProductViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductViewModel.getAll().observe(this, products -> {
            Log.d("products",products.toString());
            adapter.submitList(products);
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, ProductInputActivity.class);
                startActivity(intent);
            }
        });


    }
    
    @Override
    public void OnProductClick(int position, Product currentProduct) {
        Intent intent = new Intent(ProductActivity.this, ProductInputActivity.class);
        Log.d("product",currentProduct.toString());
        intent.putExtra("product",currentProduct);
        startActivity(intent);
    }
}