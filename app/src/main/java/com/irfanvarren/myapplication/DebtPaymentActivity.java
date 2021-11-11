package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.TextUtils;


import com.google.gson.Gson;


import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.Model.Payment;

import com.irfanvarren.myapplication.Database.PaymentRepository;

import com.google.android.material.textfield.TextInputEditText;
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

public class DebtPaymentActivity extends AppCompatActivity {
    
    private MaterialDatePicker mPaymentDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Date mPaymentDate = new Date();
    private  Double remainingAmount = new Double(0);
    private Debt mDebt;
   
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_payment);
        String name = "-";
        
        
        
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        
        Intent previousIntent = getIntent();
       
    
        if(previousIntent.getExtras() != null){
            name = previousIntent.getStringExtra("name");
            remainingAmount = previousIntent.getDoubleExtra("remainingAmount", 0);
            mDebt = (Debt) previousIntent.getSerializableExtra("debt");
        }
        
        TextView tvDistributorName = (TextView) findViewById(R.id.distributorName);
        TextView tvRemainingAmount = (TextView) findViewById(R.id.remainingAmount);
        EditText txtAmount = (EditText) findViewById(R.id.amount);
        TextInputEditText txtPaymentDate = (TextInputEditText) findViewById(R.id.paymentDate);
        TextInputEditText txtNote = (TextInputEditText) findViewById(R.id.note);
        CheckBox ckFullPayment = (CheckBox) findViewById(R.id.ckFullPayment);
        RelativeLayout payBtn = (RelativeLayout) findViewById(R.id.payBtn);
        tvDistributorName.setText(name);

        tvRemainingAmount.setText("Rp. "+nf.format(remainingAmount));
        
        txtAmount.addTextChangedListener(new TextWatcher() {
            
            public void afterTextChanged(Editable s) {
                Double amount = new Double(0);
                if (!TextUtils.isEmpty(s.toString())) {
                    amount = new Double(s.toString());
                }
                if(amount < remainingAmount){
                    ckFullPayment.setChecked(false);
                }
            }
            
            public void beforeTextChanged(CharSequence s, int start, 
            int count, int after) {
            }
            
            public void onTextChanged(CharSequence s, int start, 
            int before, int count) {
            }
        });
        
        ckFullPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtAmount.setText(String.valueOf(remainingAmount.intValue()));
                }else{
                    txtAmount.setText("0");
                }
            }
        });
        
        
        
        txtPaymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPaymentDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        
        mPaymentDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.valueOf(selection.toString());
                mPaymentDate = new DateConverter(selectedDate).getDate();
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                txtPaymentDate.setText(df.format(date));   
            }
        });
        
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double amount = new Double(0);
                if (!TextUtils.isEmpty(txtAmount.getText())) {
                    amount = Double.valueOf(txtAmount.getText().toString());
                }
                String note = txtNote.getText().toString();
                Integer debtId = null;
                Integer purchaseId = null; 
                if(mDebt != null){
                debtId = mDebt.getId();
                purchaseId = mDebt.getPurchaseId();
                }
                Payment payment = new Payment();
                payment.setAmount(amount);
                payment.setNote(note);
                payment.setPaymentDate(mPaymentDate);
                if(debtId != null){
                payment.setDebtId(debtId);
                }
                if(purchaseId != null){
                payment.setPurchaseId(purchaseId);
                }
                PaymentRepository paymentRepository = new PaymentRepository(getApplication());
                paymentRepository.insert(payment);

                Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                DebtPaymentActivity.this.finish();
            }
        });
        
        
        
    }
    
    
    
    
}