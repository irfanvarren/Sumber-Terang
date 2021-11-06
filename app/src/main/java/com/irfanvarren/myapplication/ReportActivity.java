package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.google.gson.Gson;

import android.widget.LinearLayout;
import android.widget.EditText;

import com.irfanvarren.myapplication.Model.DateConverter;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportActivity extends AppCompatActivity {
    private AutoCompleteTextView mTxtDurationType;
    private String durationType;
    private MaterialDatePicker mStartDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private MaterialDatePicker mEndDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Date mStartDate = new Date(), mEndDate = new Date();
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        LinearLayout customDateWrapper = (LinearLayout) findViewById(R.id.customDateWrapper);
        EditText startDate = (EditText) findViewById(R.id.startDate);
        EditText endDate = (EditText) findViewById(R.id.endDate);

        mTxtDurationType = findViewById(R.id.txtDurationType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_dropdown_item_1line, DURATION_TYPES);
        mTxtDurationType.setAdapter(adapter);
        mTxtDurationType.setText(adapter.getItem(0).toString(),false);
        mTxtDurationType.setThreshold(0);
        mTxtDurationType.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                durationType =  adapterView.getItemAtPosition(i).toString();
                if(durationType.equals("Pilih Tanggal")){
                    customDateWrapper.setVisibility(View.VISIBLE);                  
                    Toast.makeText(getApplicationContext(),"show hidden select date",Toast.LENGTH_SHORT).show();
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

    private static final String[] DURATION_TYPES = new String[] {
            "Hari Ini", "Bulan Ini", "Bulan Lalu", "Pilih Tanggal"
    };

}