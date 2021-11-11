package com.irfanvarren.myapplication.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Transaction;

import androidx.lifecycle.LiveData;
import com.irfanvarren.myapplication.Model.Receivable;

import java.util.List;

@Dao
public interface ReceivableDao {
    @Query("SELECT * from receivables where status = 0 order by due_date DESC")
    LiveData<List<Receivable>> getAll();

    @Transaction
    @Query("SELECT SUM(amount) from receivables where status = 0")
    LiveData<Double> getTotalReceivable();

    @Transaction
    @Query("SELECT COUNT(*) from receivables where status = 0")
    LiveData<Integer> getTotalTransaction();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Receivable... receivables);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Receivable receivable);

    @Update
    void update(Receivable receivable);

    @Delete
    void delete(Receivable receivable);
}
