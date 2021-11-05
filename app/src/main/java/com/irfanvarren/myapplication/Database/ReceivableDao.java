package com.irfanvarren.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Receivable;

@Dao
public interface ReceivableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Receivable... receivables);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Receivable receivable);

    @Update
    void update(Receivable receivable);

    @Delete
    void delete(Receivable receivable);
}
