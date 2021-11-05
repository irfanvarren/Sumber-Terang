package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Database.DistributorRepository;
import com.irfanvarren.myapplication.Model.Distributor;

public class DistributorInputActivity extends AppCompatActivity {
    private DistributorRepository mRepository;
    private String mName,mAddress,mEmail,mPhone,mBtnStatus;
    private Distributor mDistributor;
    private TextInputLayout txtName,txtAddress,txtEmail,txtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_input);

        Button addBtn = findViewById(R.id.addBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        txtName = findViewById(R.id.name);
        txtAddress = findViewById(R.id.address);
        txtEmail = findViewById(R.id.email);
        txtPhone = findViewById(R.id.phone);
        mBtnStatus = "add";

        if(getIntent().getExtras() != null){
            mDistributor = (Distributor) getIntent().getSerializableExtra("distributor");
            mBtnStatus = getIntent().getStringExtra("action");

            if(mDistributor != null) {
                txtName.getEditText().setText(mDistributor.getName());
                txtAddress.getEditText().setText(mDistributor.getAddress());
                txtEmail.getEditText().setText(mDistributor.getEmail());
                txtPhone.getEditText().setText(mDistributor.getPhone());
            }

            if(mBtnStatus.equals("edit")) {
                addBtn.setText("Ubah");
                deleteBtn.setVisibility(View.VISIBLE);
            }
        }

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mRepository = new DistributorRepository(getApplication());
                mRepository.delete(mDistributor);
                Toast.makeText(DistributorInputActivity.this, "Data distributor berhasil dihapus", Toast.LENGTH_SHORT).show();
                DistributorInputActivity.this.finish();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mName = txtName.getEditText().getText().toString();
                mEmail = txtEmail.getEditText().getText().toString();
                mPhone = txtPhone.getEditText().getText().toString();
                mAddress = txtAddress.getEditText().getText().toString();
                mRepository = new DistributorRepository(getApplication());
                if(mBtnStatus.equals("add")) {
                    mDistributor = new Distributor(mName,mEmail,mPhone,mAddress);
                    mRepository.insert(mDistributor);
                    Toast.makeText(DistributorInputActivity.this,"Data distributor berhasil ditambah", Toast.LENGTH_SHORT).show();
                }else if(mBtnStatus.equals("edit")){
                    Distributor updateDistributor = new Distributor(mDistributor.id,mName,mEmail,mPhone,mAddress);
                    mRepository.update(updateDistributor);
                    Toast.makeText(DistributorInputActivity.this, "Data distributor berhasil diupdate", Toast.LENGTH_SHORT).show();
                }

                DistributorInputActivity.this.finish();
            }
        });
    }
}