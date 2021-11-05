package com.irfanvarren.myapplication.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "other_costs")
public class OtherCost {
    @PrimaryKey(autoGenerate = true)
    public int id;
}
