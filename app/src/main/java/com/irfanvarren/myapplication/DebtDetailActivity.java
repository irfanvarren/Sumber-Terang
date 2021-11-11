package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

import com.google.gson.Gson;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.EditText;

import com.irfanvarren.myapplication.Adapter.ReportListAdapter;
import com.irfanvarren.myapplication.Database.DistributorRepository;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.Report;
import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.ViewModel.ReportViewModel;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;  
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class DebtDetailActivity extends AppCompatActivity {
    Debt mDebt;
    
    Double amountPaid = new Double(0);
    Double remainingAmount = new Double(0);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_detail);
        
        
        
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RelativeLayout payBtn = (RelativeLayout) findViewById(R.id.payBtn);
        TextView txtName = (TextView) findViewById(R.id.distributorName);
        TextView txtStatus = (TextView) findViewById(R.id.status);
        TextView txtTransactionDate = (TextView) findViewById(R.id.transactionDate);
        TextView txtDueDate = (TextView) findViewById(R.id.dueDate);
        TextView txtAmount = (TextView) findViewById(R.id.amount);
        TextView txtRemainingAmount = (TextView) findViewById(R.id.remainingAmount);
        
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        
        if(getIntent().getExtras() != null){
            mDebt = (Debt) getIntent().getSerializableExtra("debt");
            Log.d("DEBT",new Gson().toJson(mDebt));
            if(mDebt != null){
                Integer distributorId = mDebt.getDistributorId();
                DistributorRepository dRepository = new DistributorRepository(getApplication());
                Distributor distributor = dRepository.findById(distributorId);
                if(distributor != null){
                    txtName.setText(distributor.getName());
                }
                if(mDebt.getStatus()){
                    txtStatus.setText("Lunas");
                }else{
                    txtStatus.setText("Belum Lunas");
                }
                txtTransactionDate.setText(dateFormat.format(mDebt.getCreatedAt()));
                txtDueDate.setText(dateFormat.format(mDebt.getDueDate()));
                txtAmount.setText("Rp. "+ nf.format(mDebt.getAmount()));
                
                
                if(mDebt.getAmountPaid() != null){
                    amountPaid = mDebt.getAmountPaid();
                }
                
                remainingAmount = mDebt.getAmount() - amountPaid;
                txtRemainingAmount.setText("Rp. " + nf.format(remainingAmount));
            }
        }
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DebtDetailActivity.this, DebtPaymentActivity.class);
                intent.putExtra("name",txtName.getText().toString());
                intent.putExtra("remainingAmount",remainingAmount);
                intent.putExtra("debt",mDebt);
                startActivity(intent);
            }
        });
    }
    
    
    
    
}