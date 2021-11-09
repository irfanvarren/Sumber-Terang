package com.irfanvarren.myapplication.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.List;

import java.time.LocalDate; 
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import com.irfanvarren.myapplication.Model.Report;
import androidx.sqlite.db.SimpleSQLiteQuery;
import java.time.temporal.TemporalAdjusters;
import com.google.gson.Gson;
public class ReportRepository {
    private AppDatabase db;

    public ReportRepository(Application application) {
        db = AppDatabase.getDatabase(application);
    }

    public LiveData<List<Report>> getThisMonth(){
        LocalDate now = LocalDate.now();    
        String currentYear = String.valueOf(now.getYear());
        String currentMonth = String.format("%02d",now.getMonthValue());
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate startDate = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = now.with(TemporalAdjusters.lastDayOfMonth());
        
        
        Long start = startDate.atStartOfDay(zoneId).toEpochSecond() * 1000;
        Long end = endDate.atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond() * 1000;
        Log.d("START_DATE",String.valueOf(start));
        Log.d("END_DATE",String.valueOf(end));
        return db.reportDao().getThisMonth(start,end);
    }
}
