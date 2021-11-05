package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "purchase_details")
public class PurchaseDetail{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "purchase_id")
    public int purchaseId;

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


    public PurchaseDetail(int purchaseId, int productId, int qty, Double price) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.qty = qty;
        this.price = price;
    }
}
