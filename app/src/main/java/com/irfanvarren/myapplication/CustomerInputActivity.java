package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Database.CustomerRepository;
import com.irfanvarren.myapplication.Model.Customer;

public class CustomerInputActivity extends AppCompatActivity {
    private CustomerRepository mRepository;
    private String mName,mAddress,mEmail,mPhone,mBtnStatus;
    private Customer mCustomer;
    private TextInputLayout txtName,txtAddress,txtEmail,txtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_input);

        Button addBtn = findViewById(R.id.addBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        txtName = findViewById(R.id.name);
        txtAddress = findViewById(R.id.address);
        txtEmail = findViewById(R.id.email);
        txtPhone = findViewById(R.id.phone);
        mBtnStatus = "add";

        if(getIntent().getExtras() != null){
            mCustomer = (Customer) getIntent().getSerializableExtra("customer");
            mBtnStatus = getIntent().getStringExtra("action");
            if(mCustomer != null) {
                txtName.getEditText().setText(mCustomer.getName());
                txtAddress.getEditText().setText(mCustomer.getAddress());
                txtEmail.getEditText().setText(mCustomer.getEmail());
                txtPhone.getEditText().setText(mCustomer.getPhone());
            }
            if(mBtnStatus.equals("edit")) {
                addBtn.setText("Ubah");
                deleteBtn.setVisibility(View.VISIBLE);
            }
        }

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mRepository = new CustomerRepository(getApplication());
                mRepository.delete(mCustomer);
                Toast.makeText(CustomerInputActivity.this, "Data pelanggan berhasil dihapus", Toast.LENGTH_SHORT).show();
                CustomerInputActivity.this.finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mName = txtName.getEditText().getText().toString();
                mEmail = txtEmail.getEditText().getText().toString();
                mPhone = txtPhone.getEditText().getText().toString();
                mAddress = txtAddress.getEditText().getText().toString();
                mRepository = new CustomerRepository(getApplication());
                if(mBtnStatus.equals("add")) {
                    mCustomer = new Customer(mName,mEmail,mPhone,mAddress);
                    mRepository.insert(mCustomer);
                    Toast.makeText(CustomerInputActivity.this,"Data pelanggan berhasil ditambah", Toast.LENGTH_SHORT).show();
                }else if(mBtnStatus.equals("edit")){
                    Customer updateCustomer = new Customer(mCustomer.id,mName,mEmail,mPhone,mAddress);
                    mRepository.update(updateCustomer);
                    Toast.makeText(CustomerInputActivity.this, "Data pelanggan berhasil diupdate", Toast.LENGTH_SHORT).show();
                }

                CustomerInputActivity.this.finish();
            }
        });
    }
}