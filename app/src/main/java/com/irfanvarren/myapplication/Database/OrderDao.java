package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT id FROM orders ORDER BY id DESC LIMIT 1 ")
    Integer getNo();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Order... orders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

}
