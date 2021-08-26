package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity(tableName = "products")
public class Product implements Serializable {
    public String category;

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="category_id")
    public int categoryId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "qty")
    public Integer qty;

    @ColumnInfo(name="price")
    public Integer price;


    public Product(int id,int categoryId, String name, int qty, int price) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    @Ignore
    public Product(int categoryId,String name, int qty,int price) {
        this.categoryId = categoryId;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int category_id) {
        this.categoryId = category_id;
    }
}
