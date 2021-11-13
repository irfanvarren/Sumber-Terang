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

import com.irfanvarren.myapplication.Adapter.ReportDetailListAdapter;
import com.irfanvarren.myapplication.Database.DistributorRepository;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.Report;
import com.irfanvarren.myapplication.Model.ReportDetail;
import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.Model.Payment;
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

public class ReportDetailActivity extends AppCompatActivity implements ReportDetailListAdapter.OnReportDetailListener {
    
    private ReportViewModel mReportViewModel;
    private LiveData<List<ReportDetail>> mReportDetailList;
    private final ReportDetailListAdapter adapter = new ReportDetailListAdapter(new ReportDetailListAdapter.ReportDetailDiff(), this);
    private Date transactionDate;
    private Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);
        
        if(getIntent().getExtras() != null){
            report = (Report) getIntent().getSerializableExtra("report");
            transactionDate = report.getTransactionDate();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mReportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        if(transactionDate != null){
        mReportDetailList = mReportViewModel.getReportDetail(transactionDate);
        mReportDetailList.observe(this, report -> {
            adapter.submitList(report);
        });
    }

    }

    @Override
    public void ReportDetailClick(Integer position,ReportDetail reportDetail){

    }
    
}