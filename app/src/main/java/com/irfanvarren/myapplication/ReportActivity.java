package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.google.gson.Gson;

public class ReportActivity extends AppCompatActivity {
    AutoCompleteTextView mTxtDurationType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mTxtDurationType = findViewById(R.id.txtDurationType);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_dropdown_item_1line, DURATION_TYPES);
        mTxtDurationType.setAdapter(adapter);
        mTxtDurationType.setThreshold(0);

        mTxtDurationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private static final String[] DURATION_TYPES = new String[] {
            "Hari Ini", "Bulan Ini", "Bulan Lalu", "Pilih Tanggal"
    };

}