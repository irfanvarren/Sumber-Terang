package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfanvarren.myapplication.Adapter.CustomerListAdapter;
import com.irfanvarren.myapplication.Adapter.CustomerListAdapter;
import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.ViewModel.CustomerViewModel;

public class CustomerActivity extends AppCompatActivity implements CustomerListAdapter.OnCustomerListener{
    private CustomerViewModel mCustomerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final CustomerListAdapter adapter = new CustomerListAdapter(new CustomerListAdapter.CustomerDiff(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        mCustomerViewModel.getAll().observe(this, products -> {
            adapter.submitList(products);
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this, CustomerInputActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void OnCustomerClick(int position, Customer current) {
        Intent intent = new Intent(CustomerActivity.this, CustomerInputActivity.class);
        intent.putExtra("customer",current);
        intent.putExtra("action","edit");
        startActivity(intent);
    }
}
