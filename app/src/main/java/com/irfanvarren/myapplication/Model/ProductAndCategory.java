package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;

import java.io.Serializable;

public class ProductAndCategory implements Serializable {
    @ColumnInfo(name = "cart_qty")
    public Integer cartQty;

    @ColumnInfo(name = "cart_price")
    public Double cartPrice;

    @Embedded public Product product;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "id"
    )
    public Category category;

    //two level ? copy jak yang diatas sesuain lagi foreign key ny
}
