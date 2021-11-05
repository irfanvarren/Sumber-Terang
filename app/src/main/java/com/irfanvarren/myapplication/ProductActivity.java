package com.irfanvarren.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductListAdapter.OnProductListener {
    private ProductViewModel mProductViewModel;
    private LiveData<List<ProductAndCategory>> mProductsList;
    private final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff(), this);
    private List<ProductAndCategory> mProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductsList = mProductViewModel.getAll();


        mProductsList.observe(this, products -> {
            mProducts = products;
            adapter.submitList(mProducts);
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {
                    searchDatabase(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s != null) {
                    searchDatabase(s);
                }
                return true;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, ProductInputActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void OnProductClick(int position, ProductAndCategory currentProduct) {
        Intent intent = new Intent(ProductActivity.this, ProductInputActivity.class);
        intent.putExtra("product", currentProduct.product);
        intent.putExtra("category", currentProduct.category);
        intent.putExtra("action", "edit");
        startActivity(intent);
    }

    @Override
    public void OnAddClick(int position, ProductAndCategory currentProduct, View itemView) {
    }

    @Override
    public void OnSubstractClick(int position, ProductAndCategory current, View itemView) {

    }


    private void searchDatabase(String query) {
        String searchQuery = "%" + query + "%";
        mProductsList = mProductViewModel.searchDatabase(searchQuery);
        mProductsList.observe(this, products -> {
            adapter.submitList(products);
        });

    }
}