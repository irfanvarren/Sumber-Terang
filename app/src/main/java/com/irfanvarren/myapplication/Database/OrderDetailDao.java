package com.irfanvarren.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.OrderDetail;

@Dao
public interface OrderDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(OrderDetail... orderDetails);


    @Query("select COALESCE(price,0) from order_details where product_id = :id order by updated_at desc limit 1")
    public Double getLastSoldPrice(Integer id);
}
