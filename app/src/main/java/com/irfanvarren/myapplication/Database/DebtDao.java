package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Transaction;

import com.irfanvarren.myapplication.Model.Debt;

import java.util.List;


@Dao
public interface DebtDao {
    @Query("SELECT * from debts where status IN (:status) order by status, due_date DESC")
    LiveData<List<Debt>> getAllWithStatus(List<Integer> status);

    @Transaction
    @Query("SELECT (SUM(amount) - COALESCE((SELECT SUM(amount) FROM payments where payments.debt_id = debts.id),0)) as total  from debts where status = 0")
    LiveData<Double> getTotalDebt();

    @Transaction
    @Query("SELECT COUNT(*) from debts where status = 0")
    LiveData<Integer> getTotalTransaction();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Debt... debts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Debt debt);

    @Update
    void update(Debt debt);

    @Delete
    void delete(Debt debt);
}
