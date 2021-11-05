package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.irfanvarren.myapplication.Adapter.DistributorListAdapter;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.ViewModel.DistributorViewModel;

public class DistributorActivity extends AppCompatActivity  implements DistributorListAdapter.OnDistributorListener{
    private DistributorViewModel mDistributorViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final DistributorListAdapter adapter = new DistributorListAdapter(new DistributorListAdapter.DistributorDiff(),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mDistributorViewModel = new ViewModelProvider(this).get(DistributorViewModel.class);
        mDistributorViewModel.getAll().observe(this, products -> {
            adapter.submitList(products);
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DistributorActivity.this, DistributorInputActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void OnDistributorClick(int position, Distributor current) {
        Intent intent = new Intent(DistributorActivity.this, DistributorInputActivity.class);
        intent.putExtra("distributor",current);
        intent.putExtra("action","edit");
        startActivity(intent);
    }
}