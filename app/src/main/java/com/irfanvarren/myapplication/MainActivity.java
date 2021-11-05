package com.irfanvarren.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private final int APP_PERMISSION_CODE = 0;

    private void requestAppPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
           return ;
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return ;
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return ;
        }

        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},APP_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case APP_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //permission is not granted
                    //explain permission not granted
                }
                return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestAppPermission();

        LinearLayout produkBtn = (LinearLayout) findViewById(R.id.produkBtn);
        LinearLayout kategoriBtn = (LinearLayout) findViewById(R.id.kategoriBtn);
        LinearLayout stockManagementBtn = (LinearLayout) findViewById(R.id.stockManagementBtn);
        LinearLayout customerBtn = (LinearLayout) findViewById(R.id.customerBtn);
        LinearLayout distributorBtn = (LinearLayout) findViewById(R.id.distributorBtn);
        LinearLayout buyBtn = (LinearLayout) findViewById(R.id.buyBtn);
        LinearLayout sellBtn = (LinearLayout) findViewById(R.id.sellBtn);
        LinearLayout debtReceivableBtn = (LinearLayout) findViewById(R.id.hutangPiutangBtn);
        LinearLayout laporanBtn = (LinearLayout) findViewById(R.id.laporanBtn);

        produkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });

        kategoriBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        stockManagementBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, StockManagementActivity.class);
                startActivity(intent);
            }
        });

        customerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });

        distributorBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, DistributorActivity.class);
                startActivity(intent);
            }
        });

        buyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,BuyActivity.class);
                startActivity(intent);
            }
        });

        sellBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,SellActivity.class);
                startActivity(intent);
            }
        });

        debtReceivableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DebtAndReceivableActivity.class);
                startActivity(intent);
            }
        });

        laporanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,ReportActivity.class);
                startActivity(intent);
            }
        });

    }
}