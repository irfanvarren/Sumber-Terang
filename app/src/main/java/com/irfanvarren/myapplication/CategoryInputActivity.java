package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.CategoryViewModel;

public class CategoryInputActivity extends AppCompatActivity {
    private ProductRepository mRepository;
    private String name;
    private Category category;
    private TextInputLayout txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_input);

        Button addBtn = findViewById(R.id.addBtn);
        txtName = findViewById(R.id.name);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                name = txtName.getEditText().getText().toString();
                mRepository = new ProductRepository(getApplication());
                category = new Category(name);
                mRepository.insertCategory(category);
                Toast.makeText(CategoryInputActivity.this,"Data kategori berhasil ditambah", Toast.LENGTH_SHORT).show();
                CategoryInputActivity.this.finish();
            }
        });
    }
}