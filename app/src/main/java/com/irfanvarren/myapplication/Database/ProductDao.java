package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import androidx.room.RewriteQueriesToDropUnusedColumns;

import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;

import java.util.List;

@Dao
public interface ProductDao {
    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT products.*,categories.name as category FROM products INNER JOIN categories on categories.id = products.category_id")
    LiveData<List<Product>> getAll();

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT products.*,categories.name as category FROM products INNER JOIN categories on categories.id = products.category_id WHERE products.id IN (:Ids)")
    LiveData<List<Product>> loadAllByIds(int[] Ids);

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT products.id,products.category_id,products.code,products.name,products.price,products.image_path,products.note,products.created_at,products.updated_at, COALESCE((select SUM(qty) from inventory_managements where inventory_managements.product_id = products.id),0) as qty FROM products")
    public LiveData<List<ProductAndCategory>> getProductAndCategories();

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT products.id,products.category_id,products.code,products.name,products.price,products.image_path,products.note,products.created_at,products.updated_at, COALESCE((select SUM(qty) from inventory_managements where inventory_managements.product_id = products.id),0) as qty FROM products where products.name LIKE :query")
    public LiveData<List<ProductAndCategory>> searchByName(String query);

    @Query("SELECT price from products where id = :id limit 1")
    Integer getProductPrice(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Product... products);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Product products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("DELETE FROM products")
    void deleteAll();

}
