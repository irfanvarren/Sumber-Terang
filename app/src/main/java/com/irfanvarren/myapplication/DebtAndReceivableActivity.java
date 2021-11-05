package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DebtAndReceivableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt_and_receivable);

        LinearLayout debtBtn = (LinearLayout) findViewById(R.id.debtBtn);
        LinearLayout receivableBtn = (LinearLayout) findViewById(R.id.receivableBtn);
        debtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebtAndReceivableActivity.this, DebtActivity.class);
                startActivity(intent);
            }
        });

        receivableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DebtAndReceivableActivity.this, ReceivableActivity.class);
                startActivity(intent);
            }
        });
    }
}