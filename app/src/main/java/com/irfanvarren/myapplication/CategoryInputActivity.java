package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.CategoryViewModel;

public class CategoryInputActivity extends AppCompatActivity {
    private ProductRepository mRepository;
    private String mName,mBtnStatus;
    private Category mCategory;
    private TextInputLayout txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_input);

        Button addBtn = findViewById(R.id.addBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        txtName = findViewById(R.id.name);
        mBtnStatus = "add";

        if(getIntent().getExtras() != null){
            mCategory = (Category) getIntent().getSerializableExtra("category");
            mBtnStatus = getIntent().getStringExtra("action");
            if(mCategory != null) {
                txtName.getEditText().setText(mCategory.getName());
            }
            if(mBtnStatus.equals("edit")) {
                addBtn.setText("Ubah");
                deleteBtn.setVisibility(View.VISIBLE);
            }
        }


        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mRepository = new ProductRepository(getApplication());
                mRepository.deleteCategory(mCategory);
                Toast.makeText(CategoryInputActivity.this, "Data produk berhasil dihapus", Toast.LENGTH_SHORT).show();
                CategoryInputActivity.this.finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mName = txtName.getEditText().getText().toString();
                mRepository = new ProductRepository(getApplication());
                if(mBtnStatus.equals("add")) {
                    mCategory = new Category(mName);
                    mRepository.insertCategory(mCategory);
                    Toast.makeText(CategoryInputActivity.this,"Data kategori berhasil ditambah", Toast.LENGTH_SHORT).show();
                }else if(mBtnStatus.equals("edit")){
                    Category updateCategory = new Category(mCategory.id,mName);
                    mRepository.updateCategory(updateCategory);
                    Toast.makeText(CategoryInputActivity.this, "Data kategori berhasil diupdate", Toast.LENGTH_SHORT).show();
                }

                CategoryInputActivity.this.finish();
            }
        });
    }
}