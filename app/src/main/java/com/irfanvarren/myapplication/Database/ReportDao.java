package com.irfanvarren.myapplication.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Transaction;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.room.RewriteQueriesToDropUnusedColumns;

import com.irfanvarren.myapplication.Model.Report;
import java.util.List;

@Dao
public interface ReportDao {

    //format timestamp to Y-m-d in Room strftime('%Y-%m-%d',datetime(created_at/1000,'unixepoch')) as transaction_date

    @Transaction
    @Query("SELECT COUNT(*) as total_transaction, payment_date as transaction_date , COALESCE((SELECT SUM(amount) FROM payments WHERE order_id IS NOT NULL),0) AS total_sell, COALESCE((SELECT SUM(amount) FROM payments WHERE purchase_id IS NOT NULL),0) AS total_purchase FROM payments WHERE payment_date >= :start AND payment_date <= :end GROUP BY strftime('%Y-%m-%d',datetime(payment_date/1000,'unixepoch')), total_sell, total_purchase")
    LiveData<List<Report>> getThisMonth(Long start,Long end);

    @Transaction
    @Query("SELECT COALESCE(SUM(total),0) from purchases")
    LiveData<Double> getTotalExpense();

    @Transaction
    @Query("SELECT COALESCE(SUM(total),0) from purchases WHERE transaction_date >= :start AND transaction_date <= :end ")
    LiveData<Double> getTotalExpenseThisMonth(Long start, Long end);

    @Transaction
    @Query("SELECT COALESCE(SUM(total),0) from orders")
    LiveData<Double> getTotalIncome();

    @Transaction
    @Query("SELECT COALESCE(SUM(total),0) from orders WHERE transaction_date >= :start AND transaction_date <= :end")
    LiveData<Double> getTotalIncomeThisMonth(Long start, Long end);
   
}
