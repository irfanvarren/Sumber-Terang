package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.irfanvarren.myapplication.Model.Payment;

import java.util.List;

@Dao
public interface PaymentDao {

    @Query("SELECT * FROM payments WHERE debt_id = :id ORDER BY payment_date DESC")
    LiveData<List<Payment>> getAllDebtPayment(Integer id);

    @Query("SELECT * FROM payments WHERE receivable_id = :id ORDER BY payment_date DESC")
    LiveData<List<Payment>> getAllReceivablePayment(Integer id);

    @Query("SELECT id FROM payments ORDER BY id DESC LIMIT 1 ")
    Integer getNo();

    @Query("SELECT SUM(amount) FROM payments where debt_id = :id")
    Double getDebtAmountPaidTotal(Integer id);

    @Query("SELECT SUM(amount) FROM payments where receivable_id = :id")
    Double getReceivableAmountPaidTotal(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Payment... payments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Payment payment);

    @Update
    void update(Payment payment);

    @Delete
    void delete(Payment payment);

}
