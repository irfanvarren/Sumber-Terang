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
import com.irfanvarren.myapplication.Adapter.DebtListAdapter;
import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.ViewModel.DebtViewModel;

import java.util.List;

import android.os.Bundle;

public class DebtActivity extends AppCompatActivity implements DebtListAdapter.OnDebtListener{
    private DebtViewModel mDebtViewModel;
    private LiveData<List<Debt>> mDebtsList;
    private final DebtListAdapter adapter = new DebtListAdapter(new DebtListAdapter.DebtDiff(), this,getApplication());
    private List<Debt> mDebts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mDebtViewModel = new ViewModelProvider(this).get(DebtViewModel.class);
        mDebtsList = mDebtViewModel.getAll();

        Double totalDebt = mDebtViewModel.getTotalDebt();
        Integer totalTransaction = mDebtViewModel.getTotalTransaction();
        
        TextView txtTotalDebt = findViewById(R.id.totalDebt);
        TextView txtTotalTransaction = findViewById(R.id.totalTransaction);
        
        txtTotalDebt.setText(String.valueOf(totalDebt));
        txtTotalTransaction.setText(String.valueOf(totalTransaction));

        mDebtsList.observe(this, debts -> {
            mDebts = debts;
            adapter.submitList(debts);
        });

        RelativeLayout addBtn = (RelativeLayout) findViewById(R.id.addBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                
                Intent intent = new Intent(DebtActivity.this, DebtInputActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void DebtClick(Integer position,Debt debt){

        Intent intent = new Intent(DebtActivity.this, DebtDetailActivity.class);
        startActivity(intent);
    }
}