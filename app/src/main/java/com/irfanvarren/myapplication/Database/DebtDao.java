package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Debt;

import java.util.List;


@Dao
public interface DebtDao {
    @Query("SELECT * from debts order by due_date DESC")
    LiveData<List<Debt>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Debt... debts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Debt debt);

    @Update
    void update(Debt debt);

    @Delete
    void delete(Debt debt);
}
