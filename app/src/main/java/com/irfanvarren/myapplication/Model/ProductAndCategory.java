package com.irfanvarren.myapplication.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ProductAndCategory {
    @Embedded public Product product;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "id"
    )
    public Category category;
}
