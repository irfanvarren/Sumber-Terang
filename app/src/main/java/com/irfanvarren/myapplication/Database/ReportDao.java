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
import com.irfanvarren.myapplication.Model.ReportDetail;
import java.util.List;


@Dao
public interface ReportDao {

    //format timestamp to Y-m-d in Room strftime('%Y-%m-%d',datetime(created_at/1000,'unixepoch')) as transaction_date

    @Transaction
    @Query("SELECT reports.payment_date as transaction_date, COUNT(*) as total_transaction , COALESCE((SELECT SUM(amount) FROM payments WHERE order_id IS NOT NULL AND strftime('%Y-%m-%d',datetime(payments.payment_date/1000,'unixepoch')) = strftime('%Y-%m-%d',datetime(reports.payment_date/1000,'unixepoch'))),0) AS total_sell, COALESCE((SELECT SUM(amount) FROM payments WHERE purchase_id IS NOT NULL AND strftime('%Y-%m-%d',datetime(payments.payment_date/1000,'unixepoch')) = strftime('%Y-%m-%d',datetime(reports.payment_date/1000,'unixepoch'))),0) AS total_purchase FROM payments AS reports WHERE reports.payment_date >= :start AND reports.payment_date <= :end GROUP BY strftime('%Y-%m-%d',datetime(reports.payment_date/1000,'unixepoch'))")
    LiveData<List<Report>> getByDate(Long start,Long end);

    @Transaction
    @Query("SELECT * FROM (SELECT 0 AS order_id, id AS purchase_id, total, transaction_date FROM purchases UNION SELECT id AS order_id, 0 AS purchase_id, total, transaction_date FROM orders) AS report_details WHERE report_details.transaction_date >= :start AND report_details.transaction_date <= :end ORDER BY transaction_date ")
    LiveData<List<ReportDetail>> getReportDetail(Long start,Long end);

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
