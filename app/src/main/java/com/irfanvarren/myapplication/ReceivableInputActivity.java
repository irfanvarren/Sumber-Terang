package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

import com.google.gson.Gson;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.EditText;
import android.widget.ImageButton;


import com.irfanvarren.myapplication.Adapter.CustomerArrayAdapter;
import com.irfanvarren.myapplication.Model.Receivable;
import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.ViewModel.CustomerViewModel;
import com.irfanvarren.myapplication.Database.ReceivableRepository;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class ReceivableInputActivity extends AppCompatActivity  {
    
    private CustomerViewModel mCustomerViewModel;
    private Integer mCustomerId;
    private MaterialDatePicker mDueDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Date mDueDate = new Date();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable_input);
        
        AutoCompleteTextView txtCustomer = (AutoCompleteTextView) findViewById(R.id.customerId);
        RelativeLayout saveBtn = (RelativeLayout) findViewById(R.id.saveBtn);
        TextInputEditText txtAmount = (TextInputEditText) findViewById(R.id.amount);
        TextInputEditText txtNote = (TextInputEditText) findViewById(R.id.note);
        TextInputEditText dueDate = (TextInputEditText) findViewById(R.id.dueDate);
        ImageButton addCustomerBtn = (ImageButton) findViewById(R.id.addCustomerBtn);
        ReceivableRepository repository = new ReceivableRepository(getApplication());
        

        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        mCustomerViewModel.getAll().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> categories) {
                final CustomerArrayAdapter adapter = new CustomerArrayAdapter(getApplicationContext(), R.layout.auto_complete_item, categories);
                txtCustomer.setAdapter(adapter);
                txtCustomer.setThreshold(1);
            }
        });

        
    
        
        txtCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer selectedCat = (Customer) adapterView.getItemAtPosition(i);
                mCustomerId = selectedCat.getId();
            }
        });
        
        
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDueDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        
        mDueDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.valueOf(selection.toString());
                mDueDate = new DateConverter(selectedDate).getDate();
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                dueDate.setText(df.format(date));   
            }
        });

        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceivableInputActivity.this, CustomerInputActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double amount = Double.valueOf(txtAmount.getText().toString());

                if(amount <= 0){
                    Toast.makeText(getApplicationContext(), "Nominal harus lebih dari 0", Toast.LENGTH_LONG).show();
                    return;
                }else if(mCustomerId == null || mCustomerId == 0){
                    Toast.makeText(getApplicationContext(), "Customer harus dipilih", Toast.LENGTH_LONG).show();
                    return;
                }

                Receivable receivable = new Receivable();
                receivable.setCustomerId(mCustomerId);
                receivable.setAmount(amount);
                receivable.setDueDate(mDueDate);
                receivable.setNote(txtNote.getText().toString());
                receivable.setStatus(false);
                repository.insert(receivable);
                Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                ReceivableInputActivity.this.finish();
            }
        });
    }
    
    
}