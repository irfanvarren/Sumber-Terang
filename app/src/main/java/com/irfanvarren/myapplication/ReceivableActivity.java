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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.util.Log;

import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.ReceivableListAdapter;
import com.irfanvarren.myapplication.Model.Receivable;
import com.irfanvarren.myapplication.ViewModel.ReceivableViewModel;

import java.util.List;
import java.util.ArrayList;
import java.util.Locale;
import java.text.NumberFormat;

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
        
        List<Integer> status = new ArrayList<Integer>();
        status.clear();

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mReceivableViewModel = new ViewModelProvider(this).get(ReceivableViewModel.class);
        
        TextView txtTotalReceivable = (TextView) findViewById(R.id.totalReceivable);
        TextView txtTotalTransaction = (TextView) findViewById(R.id.totalTransaction);
        CheckBox ckUnpaid = (CheckBox) findViewById(R.id.ckUnpaid);
        CheckBox ckPaid = (CheckBox) findViewById(R.id.ckPaid);

        if(ckUnpaid.isChecked()){
            status.add(0);
        }

        if(ckPaid.isChecked()){
            status.add(1);
        }

        mReceivablesList = mReceivableViewModel.getAll(status);

        mReceivablesList.observe(this, receivables -> {
            mReceivables = receivables;
            adapter.submitList(receivables);
        });
       
        
        ckUnpaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
             if(b){
                 if(!status.contains((Integer) 0)){
                     status.add((Integer) 0);
                 }
                mReceivablesList = mReceivableViewModel.getAll(status);
                mReceivablesList.observe(ReceivableActivity.this, receivables -> {
                    mReceivables = receivables;
                    adapter.submitList(mReceivables);
                    adapter.notifyDataSetChanged();
                });
            }else{
                if(status.contains((Integer) 0)){
                    status.remove((Integer) 0);
                }
                mReceivablesList = mReceivableViewModel.getAll(status);
                mReceivablesList.observe(ReceivableActivity.this, receivables -> {
                    mReceivables = receivables;
                    adapter.submitList(mReceivables);
                    adapter.notifyDataSetChanged();
                });
            }
            }
        });

        ckPaid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(!status.contains((Integer) 1)){
                        status.add((Integer) 1);
                    }
                   mReceivablesList = mReceivableViewModel.getAll(status);
                   mReceivablesList.observe(ReceivableActivity.this, receivables -> {
                       mReceivables = receivables;
                       adapter.submitList(mReceivables);
                       adapter.notifyDataSetChanged();
                   });
               }else{
                   if(status.contains((Integer) 1)){
                       status.remove((Integer) 1);
                   }
                   mReceivablesList = mReceivableViewModel.getAll(status);
                   mReceivablesList.observe(ReceivableActivity.this, receivables -> {
                       mReceivables = receivables;
                       adapter.submitList(mReceivables);
                       adapter.notifyDataSetChanged();
                   });
               }
            }
        });
        

        mReceivableViewModel.getTotalReceivable().observe(this, totalReceivable -> {
            if(totalReceivable == null){
                totalReceivable = new Double(0);
            }
            txtTotalReceivable.setText("Rp. " + nf.format(totalReceivable));
        });
        mReceivableViewModel.getTotalTransaction().observe(this, totalTransaction -> {
            if(totalTransaction == null){
                totalTransaction = 0;
            }
            txtTotalTransaction.setText(String.valueOf(totalTransaction));
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
        intent.putExtra("receivable", receivable);
        startActivity(intent);
    }

}