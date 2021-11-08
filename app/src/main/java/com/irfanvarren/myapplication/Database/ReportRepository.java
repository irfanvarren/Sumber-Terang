package com.irfanvarren.myapplication.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import java.time.LocalDate; 

import com.irfanvarren.myapplication.Model.Report;
import androidx.sqlite.db.SimpleSQLiteQuery;
public class ReportRepository {
    private AppDatabase db;

    public ReportRepository(Application application) {
        db = AppDatabase.getDatabase(application);
    }

    public LiveData<List<Report>> getThisMonth(){
        LocalDate now = LocalDate.now();    
        String currentYear = String.valueOf(now.getYear());
        String currentMonth = String.format("%02d",now.getMonthValue());
        Log.d("YEAR_MONTH",currentYear + currentMonth);
        String finalQuery = "SELECT created_at as transaction_date,COALESCE((SELECT SUM(amount) FROM payments WHERE order_id IS NOT NULL),0) AS total_sell, COALESCE((SELECT SUM(amount) FROM payments WHERE purchase_id IS NOT NULL),0) AS total_purchase FROM payments WHERE strftime('%Y',created_at) = strftime('%Y',date('now')) AND  strftime('%m',created_at) = strftime('%m',date('now'))' ";
        SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery(finalQuery.toString());
        return db.reportDao().getThisMonth(simpleSQLiteQuery);
    }
}
