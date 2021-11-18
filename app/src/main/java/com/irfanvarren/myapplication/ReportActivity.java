package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import android.widget.TextView;
import android.util.Log;

import com.google.gson.Gson;

import android.widget.LinearLayout;
import android.widget.EditText;

import com.irfanvarren.myapplication.Adapter.ReportListAdapter;
import com.irfanvarren.myapplication.Model.Report;
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

public class ReportActivity extends AppCompatActivity implements ReportListAdapter.OnReportListener  {

    private AutoCompleteTextView mTxtDurationType;
    private String mDurationType = "Bulan Ini";
    private MaterialDatePicker mStartDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private MaterialDatePicker mEndDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Date mStartDate = new Date(), mEndDate = new Date();
    private Double mTotalIncome = 0.0, mTotalExpense = 0.0;

    private ReportViewModel mReportViewModel;
    private LiveData<List<Report>> mReportList;
    private final ReportListAdapter adapter = new ReportListAdapter(new ReportListAdapter.ReportDiff(), this);
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        LinearLayout customDateWrapper = (LinearLayout) findViewById(R.id.customDateWrapper);
        EditText startDate = (EditText) findViewById(R.id.startDate);
        EditText endDate = (EditText) findViewById(R.id.endDate);
        TextView txtTotalIncome = (TextView) findViewById(R.id.totalIncome);
        TextView txtTotalExpense = (TextView) findViewById(R.id.totalExpense);
        TextView txtNetProfit = (TextView) findViewById(R.id.netProfit);

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        mTxtDurationType = findViewById(R.id.txtDurationType);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mReportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);     
        mReportList = mReportViewModel.getThisMonth();
        mReportList.observe(ReportActivity.this, report -> {
            adapter.submitList(report);
        });

        mReportViewModel.getTotalIncome("All").observe(this, totalIncome -> {
            mTotalIncome = totalIncome;
            txtTotalIncome.setText("Rp. " + nf.format(totalIncome));
        });

        mReportViewModel.getTotalExpense("All").observe(this, totalExpense -> {
            mTotalExpense = totalExpense;
            txtTotalExpense.setText("Rp. " + nf.format(totalExpense));
        });

        ArrayAdapter<String> durationAdapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_dropdown_item_1line, DURATION_TYPES);
        mTxtDurationType.setAdapter(durationAdapter);
        mTxtDurationType.setText(durationAdapter.getItem(1).toString(),false);
        mTxtDurationType.setThreshold(0);
        mTxtDurationType.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDurationType =  adapterView.getItemAtPosition(i).toString();
                if(mDurationType.equals("Bulan Ini")) {
                
                mReportList = mReportViewModel.getThisMonth();
                mReportList.observe(ReportActivity.this, report -> {
                    adapter.submitList(report);
                });
                   
                }else if(mDurationType.equals("Hari Ini")){
                    mReportList = mReportViewModel.getToday();
                    mReportList.observe(ReportActivity.this, report -> {
                        adapter.submitList(report);
                    });

                }else if(mDurationType.equals("Bulan Lalu")){
                    mReportList = mReportViewModel.getLastMonth();
                    mReportList.observe(ReportActivity.this, report -> {
                        adapter.submitList(report);
                    });
                }else if(mDurationType.equals("Pilih Tanggal")){
                    //Toast.makeText(getApplicationContext(),"Fitur ini masih belum tersedia",Toast.LENGTH_SHORT).show();
                    customDateWrapper.setVisibility(View.VISIBLE);                  
                    Toast.makeText(getApplicationContext(),"show hidden select date",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Fitur ini masih belum tersedia",Toast.LENGTH_SHORT).show();
                }
            }
        });


        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEndDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });


        mStartDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.valueOf(selection.toString());
                mStartDate = new DateConverter(selectedDate).getDate();
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                startDate.setText(df.format(date));

            }
        });


        mEndDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.valueOf(selection.toString());
                mEndDate = new DateConverter(selectedDate).getDate();
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                endDate.setText(df.format(date));

            }
        });
     
    }

    @Override
    public void ReportClick(Integer position,Report report) {
        Intent intent = new Intent(getApplicationContext(), ReportDetailActivity.class);
        intent.putExtra("report", report);
        startActivity(intent);
    }

    private static final String[] DURATION_TYPES = new String[] {
            "Hari Ini", "Bulan Ini", "Bulan Lalu", "Pilih Tanggal"
    };

}