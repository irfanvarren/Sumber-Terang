package com.irfanvarren.myapplication.Model;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "products")
public class Product implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="category_id")
    public int categoryId;

    @ColumnInfo(name="code")
    public String code;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "qty")
    public Integer qty;

    @ColumnInfo(name="price")
    public Integer price;

    @ColumnInfo(name="image_path")
    public String imagePath;

    @ColumnInfo(name="note")
    public String note;

    @ColumnInfo(name="created_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date createdAt;

    @ColumnInfo(name="updated_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date updatedAt;

    public Product(int id, int categoryId, String code, String name, Integer qty, Integer price, String imagePath, String note) {
        this.id = id;
        if(categoryId != 0) {
            this.categoryId = categoryId;
        }
        if(!TextUtils.isEmpty(code)) {
            this.code = code;
        }
        this.name = name;
        this.qty = qty;
        this.price = price;
        if(!TextUtils.isEmpty(imagePath)) {
            this.imagePath = imagePath;
        }
        if(!TextUtils.isEmpty(note)) {
            this.note = note;
        }
    }

    @Ignore
    public Product(int categoryId, String code, String name, Integer qty, Integer price, String imagePath, String note) {
        if(categoryId != 0) {
            this.categoryId = categoryId;
        }
        if(!TextUtils.isEmpty(code)) {
            this.code = code;
        }
        this.name = name;
        this.qty = qty;
        this.price = price;
        if(!TextUtils.isEmpty(imagePath)) {
            this.imagePath = imagePath;
        }
        if(!TextUtils.isEmpty(note)) {
            this.note = note;
        }
    }

    @Ignore
    public Product(int categoryId,String name,Integer qty,Integer price){
        if(categoryId != 0) {
            this.categoryId = categoryId;
        }
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    @Ignore
    public Product() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

  
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int category_id) {
        this.categoryId = category_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
