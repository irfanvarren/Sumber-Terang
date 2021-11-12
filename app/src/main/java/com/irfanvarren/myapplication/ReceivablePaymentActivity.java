package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;
import android.content.Context;

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

import com.irfanvarren.myapplication.Model.Receivable;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.Model.Payment;

import com.irfanvarren.myapplication.Database.PaymentRepository;
import com.irfanvarren.myapplication.Database.ReceivableRepository;

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

public class ReceivablePaymentActivity extends AppCompatActivity {
    private Integer REQUEST_CODE = 1;
    private MaterialDatePicker mPaymentDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Date mPaymentDate = new Date();
    private  Double remainingAmount = new Double(0);
    private Receivable mReceivable;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable_payment);
        String name = "-";
        
        
        
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        
        Intent previousIntent = getIntent();
        
        
        if(previousIntent.getExtras() != null){
            name = previousIntent.getStringExtra("name");
            remainingAmount = previousIntent.getDoubleExtra("remainingAmount", 0);
            mReceivable = (Receivable) previousIntent.getSerializableExtra("receivable");
        }
        
        TextView tvCustomerName = (TextView) findViewById(R.id.customerName);
        TextView tvRemainingAmount = (TextView) findViewById(R.id.remainingAmount);
        EditText txtAmount = (EditText) findViewById(R.id.amount);
        TextInputEditText txtPaymentDate = (TextInputEditText) findViewById(R.id.paymentDate);
        TextInputEditText txtNote = (TextInputEditText) findViewById(R.id.note);
        CheckBox ckFullPayment = (CheckBox) findViewById(R.id.ckFullPayment);
        RelativeLayout payBtn = (RelativeLayout) findViewById(R.id.payBtn);
        RelativeLayout amountWrapper = (RelativeLayout) findViewById(R.id.amountWrapper);
        
        amountWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAmount.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm != null){
                imm.showSoftInput(txtAmount, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        
        tvCustomerName.setText(name);
        
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

                if(amount <= 0){
                    Toast.makeText(getApplicationContext(), "Nominal harus lebih dari 0", Toast.LENGTH_LONG).show();
                    return;
                }else if(amount > remainingAmount){
                    Toast.makeText(getApplicationContext(), "Nominal harus lebih kecil dari sisa piutang", Toast.LENGTH_LONG).show();
                    return;
                }

                String note = txtNote.getText().toString();
                Integer receivableId = null;
                Integer orderId = null; 
                if(mReceivable != null){
                    receivableId = mReceivable.getId();
                    orderId = mReceivable.getOrderId();
                }
                Payment payment = new Payment();
                payment.setAmount(amount);
                payment.setNote(note);
                payment.setPaymentDate(mPaymentDate);
                if(receivableId != null){
                    payment.setReceivableId(receivableId);
                }
                if(orderId != null){
                    payment.setOrderId(orderId);
                }
                PaymentRepository paymentRepository = new PaymentRepository(getApplication());
                paymentRepository.insert(payment);
                
                if(mReceivable != null){
                    Double amountPaid = new Double(0);
                    if(paymentRepository.getReceivableAmountPaidTotal(mReceivable.getId()) != null){
                        amountPaid = paymentRepository.getReceivableAmountPaidTotal(mReceivable.getId());
                    }
                    
                    ReceivableRepository receivableRepository = new ReceivableRepository(getApplication());
                    mReceivable.setAmountPaid(amountPaid);
                    if(amountPaid >= mReceivable.getAmount()){
                        mReceivable.setStatus(true);
                    }
                    Log.d("AMOUNT_PAID",String.valueOf(amountPaid));
                    Log.d("DEBT_PAYMENT",new Gson().toJson(mReceivable));
                    receivableRepository.update(mReceivable);
                }
                
                Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                setResult(REQUEST_CODE);
                ReceivablePaymentActivity.this.finish();
            }
        });
        
        
        
    }
    
    
    
    
}