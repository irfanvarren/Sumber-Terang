package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator; 

import android.view.ViewGroup;
import android.widget.LinearLayout;
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


        mDebtsList.observe(this, debts -> {
            mDebts = debts;
            adapter.submitList(debts);
        });

    }

    @Override
    public void DebtClick(Integer position,Debt debt){

    }
}