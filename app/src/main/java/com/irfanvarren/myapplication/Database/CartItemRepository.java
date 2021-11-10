package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.Model.PurchaseDetail;

import java.util.Date;
import java.util.List;

public class CartItemRepository {
    private CartItemDao mCartItemDao;
    private LiveData<List<ProductAndCategory>> mAllProductAndCategories;
    private AppDatabase db;

    public CartItemRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mCartItemDao = db.cartItemDao();
    }

    public LiveData<List<ProductAndCategory>> getAllProductAndCategory(){
        mAllProductAndCategories = mCartItemDao.getProductAndCategories();
        return mAllProductAndCategories;
    }

    public LiveData<List<ProductAndCategory>> searchProductByName(String query){
        return mCartItemDao.searchByName(query);
    }

    public long insert(CartItem cartItem) {
        long id = db.cartItemDao().insert(cartItem);
        return id;
    }

    

    public void insertAll(CartItem... cartItems){
        db.cartItemDao().insertAll(cartItems);
    }

    public void update(CartItem cartItem){
        db.cartItemDao().update(cartItem);
    }


    public void update(Integer productId, Integer qty, Double price){
        db.cartItemDao().updateQtyPrice(productId,qty,price);
    }

    public void delete(CartItem cartItem){
        db.cartItemDao().delete(cartItem);
    }

    public void deleteByProductId(Integer productId){
        db.cartItemDao().deleteByProductId(productId);
    }

    public void deleteAll(){
        db.cartItemDao().deleteAll();
    }

}
