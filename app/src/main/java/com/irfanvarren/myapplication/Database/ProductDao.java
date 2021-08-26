package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT products.*,categories.name as category FROM products INNER JOIN categories on categories.id = products.category_id")
    LiveData<List<Product>> getAll();

    @Query("SELECT products.*,categories.name as category FROM products INNER JOIN categories on categories.id = products.category_id WHERE products.id IN (:Ids)")
    LiveData<List<Product>> loadAllByIds(int[] Ids);

    @Transaction
    @Query("SELECT * FROM products")
    public LiveData<List<ProductAndCategory>> getProductAndCategories();

    @Insert
    void insertAll(Product... products);

    @Update
    void updateAll(Product... products);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM products")
    void deleteAll();
}
