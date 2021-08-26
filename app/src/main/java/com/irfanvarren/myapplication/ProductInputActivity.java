package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Adapter.CategoryArrayAdapter;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.CategoryViewModel;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.util.List;

public class ProductInputActivity extends AppCompatActivity{
    private ProductRepository mRepository;
    private String name,btnStatus;
    private Integer qty,price,category_id;
    private Product product;
    private TextInputLayout txtName,txtQty,txtPrice;
    private AutoCompleteTextView txtCategory;
    private CategoryViewModel mCategoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_input);

        Button addBtn = findViewById(R.id.addBtn);
        txtName = findViewById(R.id.name);
        txtQty = findViewById(R.id.qty);
        txtPrice =  findViewById(R.id.price);
        txtCategory = findViewById(R.id.category_id);
        btnStatus = "add";

        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAll().observe(this, new Observer<List<Category>>(){
            @Override
            public void onChanged(List<Category> categories) {
                final CategoryArrayAdapter adapter = new CategoryArrayAdapter(ProductInputActivity.this,R.layout.auto_complete_item,categories);
                txtCategory.setAdapter(adapter);
            }
        });

        txtCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("adapter view item : ",adapterView.getSelectedItem().toString());
                Log.d("selected item index : ",String.valueOf(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if(getIntent().getExtras() != null){
            Product current = (Product) getIntent().getSerializableExtra("product");
            txtName.getEditText().setText(current.getName());
            txtQty.getEditText().setText(String.valueOf(current.getQty()));
            txtPrice.getEditText().setText(String.valueOf(current.getPrice()));
            Log.d("check category",current.getCategory());
            addBtn.setText("Ubah");
            btnStatus = "edit";
        }

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                name = txtName.getEditText().getText().toString();
                qty = Integer.parseInt(txtQty.getEditText().getText().toString());
                price = Integer.parseInt(txtPrice.getEditText().getText().toString());
                product = new Product(1,name,qty,price);
                mRepository = new ProductRepository(getApplication());
                if(btnStatus=="add") {
                    mRepository.insertProduct(product);
                    Toast.makeText(ProductInputActivity.this, "Data produk berhasil ditambah", Toast.LENGTH_SHORT).show();
                }else if(btnStatus == "edit"){
                    mRepository.updateProduct(product);
                    Toast.makeText(ProductInputActivity.this, "Data produk berhasil diupdate", Toast.LENGTH_SHORT).show();
                }
                ProductInputActivity.this.finish();
            }
        });
    }



}

