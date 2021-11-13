package com.irfanvarren.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Adapter.CategoryArrayAdapter;
import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.CategoryViewModel;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductInputActivity extends AppCompatActivity{
    private ProductRepository mRepository;
    private String code,name,imagePath,note,btnStatus;
    private Integer qty,stockPrice,price,mCategoryId = 0;
    private TextInputLayout txtCode,txtName,txtQty,txtPrice,txtStockPrice,txtNote;
    private AutoCompleteTextView txtCategory;
    private CategoryViewModel mCategoryViewModel;
    private Product mProduct;
    private Category mCategory;
    private Bitmap mProductBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_input);

        Button addBtn = findViewById(R.id.addBtn);
        ImageButton addCategoryBtn = findViewById(R.id.addCategoryBtn);
        Button deleteBtn = findViewById(R.id.deleteBtn);
        ImageButton cameraBtn = findViewById(R.id.cameraBtn);
        ImageButton galleryBtn = findViewById(R.id.galleryBtn);
        ImageButton closeImageButton = findViewById(R.id.closeImageBtn);
        ImageView imgProduct = (ImageView) findViewById(R.id.imgProduct);
        LinearLayout stockManagementBtn = findViewById(R.id.stockManagementBtn);

        txtName = findViewById(R.id.name);
        txtQty = findViewById(R.id.qty);
        txtStockPrice = findViewById(R.id.stockPrice);
        txtPrice =  findViewById(R.id.price);
        txtCategory = findViewById(R.id.categoryId);
        txtCode = findViewById(R.id.code);
        txtNote = findViewById(R.id.note);
        btnStatus = "add";

    stockManagementBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(ProductInputActivity.this, StockManagementViewActivity.class);
            intent.putExtra("product",mProduct);
            intent.putExtra("category",mCategory);
            startActivity(intent);
        }
    });

        ActivityResultLauncher<Intent> cameraActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            mProductBitmap = (Bitmap) data.getExtras().get("data");
                            imgProduct.setImageBitmap(mProductBitmap);
                            closeImageButton.setVisibility(View.VISIBLE);
                        }else{
                            closeImageButton.setVisibility(View.GONE);
                        }

                    }
                }
        );

        cameraBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                            cameraActivityLauncher.launch(takePictureIntent);
                    }
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                    Log.e("media error","cannot start camera intent");
                }

            }
        });



        ActivityResultLauncher<Intent> galleryActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent data = result.getData();
                        if (result.getResultCode() == RESULT_OK && data != null) {
                            Uri imageUri = data.getData();
                            ContentResolver cr = getContentResolver();
                            InputStream inputStream = null;
                            try {
                                inputStream = cr.openInputStream(imageUri);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            mProductBitmap = BitmapFactory.decodeStream(inputStream);
                            imgProduct.setImageURI(imageUri);
                            closeImageButton.setVisibility(View.VISIBLE);
                        }else{
                            closeImageButton.setVisibility(View.GONE);
                        }
                    }
                }
        );

        galleryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                try {
                    galleryActivityLauncher.launch(takeGalleryIntent);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                    Log.e("media error","cannot start gallery intent");
                }
            }
        });


        closeImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                imgProduct.setImageResource(R.drawable.ic_baseline_image_not_supported_24);
                closeImageButton.setVisibility(View.GONE);
            }
        });






        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAll().observe(this, new Observer<List<Category>>(){
            @Override
            public void onChanged(List<Category> categories) {
                final CategoryArrayAdapter adapter = new CategoryArrayAdapter(ProductInputActivity.this,R.layout.auto_complete_item,categories);
                txtCategory.setAdapter(adapter);
            }
        });

        txtCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category selectedCat = (Category) adapterView.getItemAtPosition(i);
                mCategoryId = selectedCat.getId();
            }
        });


        if(getIntent().getExtras() != null){
            mProduct = (Product) getIntent().getSerializableExtra("product");
            mCategory = (Category) getIntent().getSerializableExtra("category");
            btnStatus = getIntent().getStringExtra("action");
            imagePath = mProduct.getImagePath();
            txtCode.getEditText().setText(mProduct.getCode());
            txtName.getEditText().setText(mProduct.getName());
            txtNote.getEditText().setText(mProduct.getNote());
            txtPrice.getEditText().setText(String.valueOf(mProduct.getPrice()));
            txtQty.setVisibility(View.GONE);
            txtStockPrice.setVisibility(View.GONE);
            stockManagementBtn.setVisibility(View.VISIBLE);
            if(mCategory != null) {
                txtCategory.setText(mCategory.getName());
                mCategoryId = mCategory.getId();
            }
            if(btnStatus.equals("edit")) {
                if(!TextUtils.isEmpty(mProduct.getImagePath())){
                    Bitmap bitmapProduct = loadImageFromStorage("products",mProduct.getImagePath());

                    imgProduct.setImageBitmap(bitmapProduct);
                }
                deleteBtn.setVisibility(View.VISIBLE);
                addBtn.setText("Ubah");
            }
        }

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                name = txtName.getEditText().getText().toString();

                qty = 0;
                if(txtQty.getEditText().getText().toString().trim().length() != 0) {
                    qty = Integer.parseInt(txtQty.getEditText().getText().toString());
                }

                stockPrice = 0;
                if(txtStockPrice.getEditText().getText().toString().trim().length() != 0) {
                    stockPrice = Integer.parseInt(txtStockPrice.getEditText().getText().toString());
                }

                price = 0;
                if(txtPrice.getEditText().getText().toString().trim().length() != 0 && txtQty.getEditText().getText().toString().trim().length() != 0) {
                    price = Integer.parseInt(txtPrice.getEditText().getText().toString());
                }
                code = txtCode.getEditText().getText().toString();
                note = txtNote.getEditText().getText().toString();
                mRepository = new ProductRepository(getApplication());
                if(mProductBitmap != null) {
                    String filename = "";

                    if(!TextUtils.isEmpty(code)){
                        filename = code;
                    }else {
                        if(btnStatus.equals("edit")){
                            filename= mProduct.getImagePath();
                        }
                    }
                    if(!TextUtils.isEmpty(filename)){
                        imagePath = saveToInternalStorage(mProductBitmap,"products",filename);
                    }else{
                        imagePath = saveToInternalStorage(mProductBitmap, "products");
                    }
                }
                if(btnStatus.equals("add")) {
                    Product insertProduct = new Product(mCategoryId,code,name,qty,price,imagePath,note);
                    int newProductId = (int) mRepository.insertProduct(insertProduct);
                    if(qty > 0){
                        Inventory insertInventory = new Inventory(newProductId,0,"","in","first-stock",name,qty,price);
                        InventoryRepository iRepository = new InventoryRepository(getApplication());
                        iRepository.insert(insertInventory);
                    }
                    Toast.makeText(ProductInputActivity.this, "Data produk berhasil ditambah", Toast.LENGTH_SHORT).show();
                }else if(btnStatus.equals("edit")){
                    Product updateProduct = new Product(mProduct.id,mCategoryId,code,name,qty,price,imagePath,note);
                    mRepository.updateProduct(updateProduct);
                    if(!mProduct.getName().equals(name)){
                        InventoryRepository iRepository = new InventoryRepository(getApplication());
                        iRepository.updateProductName(mProduct.id,name);
                    }
                    Toast.makeText(ProductInputActivity.this, "Data produk berhasil diupdate", Toast.LENGTH_SHORT).show();
                }
                ProductInputActivity.this.finish();
            }
        });

        addCategoryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductInputActivity.this, CategoryInputActivity.class);
                intent.putExtra("action","add");
                startActivity(intent);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                InventoryRepository iRepository = new InventoryRepository(getApplication());
                iRepository.deleteByProductId(mProduct.id);

                if(!TextUtils.isEmpty(mProduct.getImagePath())){
                    deleteImageFromStorage("products",mProduct.getImagePath());
                }

                mRepository = new ProductRepository(getApplication());
                mRepository.deleteProduct(mProduct);

                Toast.makeText(ProductInputActivity.this, "Data produk berhasil dihapus", Toast.LENGTH_SHORT).show();
                ProductInputActivity.this.finish();

            }
        });
    }

        private String saveToInternalStorage(Bitmap bitmapImage,String dir) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir(dir, Context.MODE_PRIVATE);
            Long ts = System.currentTimeMillis()/1000;
            String filename = ts.toString()+".png";
            File mypath = new File(directory, filename);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                Log.d("SAVE FILE","Error Cannot Save FIle");
                e.printStackTrace();
            } finally {
                try{
                    fos.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            return filename;
        }

    private String saveToInternalStorage(Bitmap bitmapImage,String dir,String filename) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        if(filename.contains(".")){
            filename = filename.substring(0,filename.lastIndexOf("."));
        }
        filename = filename+".png";
        File mypath = new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            Log.d("SAVE FILE","Error Cannot Save FIle");
            e.printStackTrace();
        } finally {
            try{
                fos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return filename;
    }

        private Bitmap loadImageFromStorage(String dir, String filename){
            Bitmap b = null;
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir(dir, Context.MODE_PRIVATE);
            try{
                File f = new File(directory, filename);
                b = BitmapFactory.decodeStream(new FileInputStream(f));
            }catch(FileNotFoundException e){
            e.printStackTrace();
            }
            return b;
        }

    private int deleteImageFromStorage(String dir, String filename){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        File f = new File(directory, filename);
        f.delete();
        return 0;
    }
}


