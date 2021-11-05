package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irfanvarren.myapplication.Database.CartItemRepository;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mRepository;
    private CartItemRepository cartRepository;
    private LiveData<List<ProductAndCategory>> mAllProducts;

    public ProductViewModel (Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        cartRepository = new CartItemRepository(application);
        mAllProducts = mRepository.getAllProductAndCategory();
    }

    public LiveData<List<ProductAndCategory>> getAll() { return mAllProducts; }

    public LiveData<List<ProductAndCategory>> searchDatabase(String query){
        return mRepository.searchProductByName(query);
    }

    public LiveData<List<ProductAndCategory>> getAllWithCart() {
        return cartRepository.getAllProductAndCategory();
    }

    public LiveData<List<ProductAndCategory>> searchDatabaseWithCart(String query){
        return cartRepository.searchProductByName(query);
    }

    public void insert(Product product) { mRepository.insertProduct(product); }
}
