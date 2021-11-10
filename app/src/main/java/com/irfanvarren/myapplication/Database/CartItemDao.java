package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.ProductAndCategory;

import java.util.List;

@Dao
public interface CartItemDao {

    @Transaction
    @Query("SELECT products.id,products.category_id,products.code,products.name,products.price,products.image_path,products.note,products.created_at,products.updated_at, COALESCE((select SUM(qty) from inventory_managements where inventory_managements.product_id = products.id),0) as qty, COALESCE(cart_items.qty,0) as cart_qty, COALESCE(cart_items.price,0) as cart_price FROM products LEFT JOIN cart_items on cart_items.product_id = products.id")
    public LiveData<List<ProductAndCategory>> getProductAndCategories();

    @Transaction
    @Query("SELECT products.id,products.category_id,products.code,products.name,products.price,products.image_path,products.note,products.created_at,products.updated_at, COALESCE((select SUM(qty) from inventory_managements where inventory_managements.product_id = products.id),0) as qty, COALESCE(cart_items.qty,0) as cart_qty, COALESCE(cart_items.price,0) as cart_price FROM products LEFT JOIN cart_items on cart_items.product_id = products.id where products.name LIKE :query")
    public LiveData<List<ProductAndCategory>> searchByName(String query);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CartItem... cartItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CartItem cartItem);

    @Update
    void update(CartItem cartItem);

    @Query("UPDATE cart_items set qty = :qty, price = :price where product_id = :productId")
    void updateQtyPrice(Integer productId, Integer qty, Double price);

    @Delete
    void delete(CartItem cartItem);

    @Query("DELETE FROM cart_items")
    void deleteAll();

    @Query("DELETE FROM cart_items WHERE product_id = :productId")
    void deleteByProductId(Integer productId);

}
