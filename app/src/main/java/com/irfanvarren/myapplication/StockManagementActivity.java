package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.util.List;

public class StockManagementActivity extends AppCompatActivity implements ProductListAdapter.OnProductListener {
    private ProductViewModel mProductViewModel;
    private LiveData<List<ProductAndCategory>> mProductsList;
    private final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff(),this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_management);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductsList = mProductViewModel.getAll();

        mProductsList.observe(this, products -> {
            adapter.submitList(products);
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s != null) {
                    searchDatabase(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s != null) {
                    searchDatabase(s);
                }
                return true;
            }
        });



    }

    @Override
    public void OnProductClick(int position, ProductAndCategory currentProduct) {
        Intent intent = new Intent(StockManagementActivity.this, StockManagementViewActivity.class);
        intent.putExtra("product",currentProduct.product);
        intent.putExtra("category",currentProduct.category);
        startActivity(intent);
    }

    @Override
    public void OnAddClick(int position, ProductAndCategory currentProduct,View itemView) {
    }

    @Override
    public void OnSubstractClick(int position, ProductAndCategory current, View itemView) {

    }


    private void searchDatabase(String query){
        String searchQuery = "%"+query+"%";
        mProductsList = mProductViewModel.searchDatabase(searchQuery);
        mProductsList.observe(this,products -> {
            adapter.submitList(products);
        });

    }
}