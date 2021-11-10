package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator; 

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.ReceivableListAdapter;
import com.irfanvarren.myapplication.Model.Receivable;
import com.irfanvarren.myapplication.ViewModel.ReceivableViewModel;

import java.util.List;

import android.os.Bundle;

public class ReceivableActivity extends AppCompatActivity implements ReceivableListAdapter.OnReceivableListener{
    private ReceivableViewModel mReceivableViewModel;
    private LiveData<List<Receivable>> mReceivablesList;
    private final ReceivableListAdapter adapter = new ReceivableListAdapter(new ReceivableListAdapter.ReceivableDiff(), this,getApplication());
    private List<Receivable> mReceivables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mReceivableViewModel = new ViewModelProvider(this).get(ReceivableViewModel.class);
        mReceivablesList = mReceivableViewModel.getAll();

        Double totalReceivable = mReceivableViewModel.getTotalReceivable();
        Integer totalTransaction = mReceivableViewModel.getTotalTransaction();
        
        TextView txtTotalReceivable = findViewById(R.id.totalReceivable);
        TextView txtTotalTransaction = findViewById(R.id.totalTransaction);
        
        txtTotalReceivable.setText(String.valueOf(totalReceivable));
        txtTotalTransaction.setText(String.valueOf(totalTransaction));

        mReceivablesList.observe(this, receivables -> {
            mReceivables = receivables;
            adapter.submitList(receivables);
        });

        RelativeLayout addBtn = (RelativeLayout) findViewById(R.id.addBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                
                Intent intent = new Intent(ReceivableActivity.this, ReceivableInputActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void ReceivableClick(Integer position,Receivable receivable){

        Intent intent = new Intent(ReceivableActivity.this, ReceivableDetailActivity.class);
        startActivity(intent);
    }
}