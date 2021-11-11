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


import com.irfanvarren.myapplication.Adapter.DistributorArrayAdapter;
import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.ViewModel.DistributorViewModel;
import com.irfanvarren.myapplication.Database.DebtRepository;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class DebtInputActivity extends AppCompatActivity  {
    
    private DistributorViewModel mDistributorViewModel;
    private Integer mDistributorId;
    private MaterialDatePicker mDueDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Date mDueDate = new Date();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_input);
        
        AutoCompleteTextView txtDistributor = (AutoCompleteTextView) findViewById(R.id.distributorId);
        RelativeLayout saveBtn = (RelativeLayout) findViewById(R.id.saveBtn);
        TextInputEditText txtAmount = (TextInputEditText) findViewById(R.id.amount);
        TextInputEditText txtNote = (TextInputEditText) findViewById(R.id.note);
        TextInputEditText dueDate = (TextInputEditText) findViewById(R.id.dueDate);
        ImageButton addDistributorBtn = (ImageButton) findViewById(R.id.addDistributorBtn);
        DebtRepository repository = new DebtRepository(getApplication());
        

        mDistributorViewModel = new ViewModelProvider(this).get(DistributorViewModel.class);
        mDistributorViewModel.getAll().observe(this, new Observer<List<Distributor>>() {
            @Override
            public void onChanged(List<Distributor> categories) {
                final DistributorArrayAdapter adapter = new DistributorArrayAdapter(getApplicationContext(), R.layout.auto_complete_item, categories);
                txtDistributor.setAdapter(adapter);
                txtDistributor.setThreshold(1);
            }
        });

        
    
        
        txtDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Distributor selectedCat = (Distributor) adapterView.getItemAtPosition(i);
                mDistributorId = selectedCat.getId();
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

        addDistributorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebtInputActivity.this, DistributorInputActivity.class);
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
                }else if(mDistributorId == null || mDistributorId == 0){
                    Toast.makeText(getApplicationContext(), "Distributor harus dipilih", Toast.LENGTH_LONG).show();
                    return;
                }

                Debt debt = new Debt();
                debt.setDistributorId(mDistributorId);
                debt.setAmount(amount);
                debt.setDueDate(mDueDate);
                debt.setNote(txtNote.getText().toString());
                debt.setStatus(false);
                repository.insert(debt);
                Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_LONG).show();
                DebtInputActivity.this.finish();
            }
        });
    }
    
    
}