package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Inventory;

import java.util.List;

@Dao
public interface InventoryDao {
    @Query("SELECT * from inventory_managements order by created_at DESC")
    LiveData<List<Inventory>> getAll();

    @Query("SELECT * from inventory_managements where product_id = :id order by created_at DESC")
    LiveData<List<Inventory>> getByProductId(int id);

    @Query("SELECT COALESCE(SUM(qty * price),0) as total from inventory_managements where product_id = :id")
    LiveData<Double> getTotalAsset(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Inventory... inventories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Inventory inventory);

    @Update
    void update(Inventory inventory);

    @Query("UPDATE inventory_managements SET product_name=:name WHERE product_id=:productId")
    void updateProductName(int productId,String name);

    @Query("UPDATE inventory_managements SET product_name=:name, price=:price WHERE id=:id")
    void update(int id,String name,int price);

    @Delete
    void delete(Inventory inventory);

    @Query("DELETE FROM inventory_managements")
    void deleteAll();

    @Query("DELETE from inventory_managements where product_id = :id")
    void deleteByProductId(int id);
}
