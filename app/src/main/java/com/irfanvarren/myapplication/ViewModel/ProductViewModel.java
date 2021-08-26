package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Product;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mRepository;

    private final LiveData<List<Product>> mAllProducts;

    public ProductViewModel (Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllProducts = mRepository.getAllProduct();
    }

    public LiveData<List<Product>> getAll() { return mAllProducts; }

    public void insert(Product product) { mRepository.insertProduct(product); }

}
