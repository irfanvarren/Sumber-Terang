package com.irfanvarren.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Debt;

@Dao
public interface DebtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Debt... debts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Debt debt);

    @Update
    void update(Debt debt);

    @Delete
    void delete(Debt debt);
}
