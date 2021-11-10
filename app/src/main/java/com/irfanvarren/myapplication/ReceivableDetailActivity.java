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
import android.util.Log;

import com.google.gson.Gson;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.EditText;

import com.irfanvarren.myapplication.Adapter.ReportListAdapter;
import com.irfanvarren.myapplication.Model.Report;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.ViewModel.ReportViewModel;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class ReceivableDetailActivity extends AppCompatActivity {
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivable_detail);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RelativeLayout payBtn = (RelativeLayout) findViewById(R.id.payBtn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceivableDetailActivity.this, ReceivablePaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    


}