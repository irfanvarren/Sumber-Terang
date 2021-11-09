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

    @Transaction
    @Query("SELECT COUNT(*) as total_transaction,strftime('%Y-%m-%d',datetime(created_at/1000,'unixepoch')) as transaction_date , COALESCE((SELECT SUM(amount) FROM payments WHERE order_id IS NOT NULL),0) AS total_sell, COALESCE((SELECT SUM(amount) FROM payments WHERE purchase_id IS NOT NULL),0) AS total_purchase FROM payments WHERE created_at >= :start AND created_at <= :end GROUP BY strftime('%Y-%m-%d',datetime(created_at/1000,'unixepoch')), total_sell, total_purchase")
    LiveData<List<Report>> getThisMonth(Long start,Long end);
   
}
