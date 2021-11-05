package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "order_details")
public class OrderDetail{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "order_id")
    public int orderId;

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "qty")
    public int qty;

    @ColumnInfo(name = "price")
    public Double price;

    @ColumnInfo(name="created_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date createdAt;

    @ColumnInfo(name="updated_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date updatedAt;


    public OrderDetail(int orderId, int productId, int qty, Double price) {
        this.orderId = orderId;
        this.productId = productId;
        this.qty = qty;
        this.price = price;
    }
}
