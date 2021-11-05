package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Purchase;

import java.util.List;

@Dao
public interface PurchaseDao {

    @Query("SELECT id FROM purchases ORDER BY id DESC LIMIT 1 ")
    Integer getNo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Purchase... purchases);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Purchase purchase);

    @Update
    void update(Purchase purchase);

    @Delete
    void delete(Purchase purchase);

}
