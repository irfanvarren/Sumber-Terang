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
import com.irfanvarren.myapplication.Model.ReportDetail;
import androidx.sqlite.db.SimpleSQLiteQuery;
import java.time.temporal.TemporalAdjusters;
import com.google.gson.Gson;
public class ReportRepository {
    private AppDatabase db;
    
    public ReportRepository(Application application) {
        db = AppDatabase.getDatabase(application);
    }
    
    public LiveData<List<ReportDetail>> getReportDetail(Date date){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate transactionDate = date.toInstant().atZone(zoneId).toLocalDate();  
        LocalDate startDate = transactionDate.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = transactionDate.with(TemporalAdjusters.lastDayOfMonth());
        Long start = startDate.atStartOfDay(zoneId).toEpochSecond() * 1000;
        Long end = endDate.atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond() * 1000;
        return db.reportDao().getReportDetail(start,end);
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
        
        return db.reportDao().getByDate(start,end);
    }

    public LiveData<List<Report>> getToday(){
        LocalDate now = LocalDate.now();    
        ZoneId zoneId = ZoneId.systemDefault();
        Long start = now.atStartOfDay(zoneId).toEpochSecond() * 1000;
        Long end = now.atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond() * 1000;
        
        return db.reportDao().getByDate(start,end);
    }

    public LiveData<List<Report>> getLastMonth(){
        LocalDate now = LocalDate.now();    
        LocalDate earlier = now.minusMonths(1);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate startDate = earlier.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = earlier.with(TemporalAdjusters.lastDayOfMonth());
        
        Long start = startDate.atStartOfDay(zoneId).toEpochSecond() * 1000;
        Long end = endDate.atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond() * 1000;

        return db.reportDao().getByDate(start,end);
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
}
    
    
    public LiveData<Double> getTotalIncome(String durationType){
        if(durationType.equals("Bulan Ini")){
            LocalDate now = LocalDate.now();    
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate startDate = now.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate endDate = now.with(TemporalAdjusters.lastDayOfMonth());
            Long start = startDate.atStartOfDay(zoneId).toEpochSecond() * 1000;
            Long end = endDate.atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond() * 1000;
            return db.reportDao().getTotalIncomeThisMonth(start, end);
        }

        return db.reportDao().getTotalIncome();
    }
    
    public LiveData<Double> getTotalExpense(String durationType){
        if(durationType.equals("Bulan Ini")){
            LocalDate now = LocalDate.now();    
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate startDate = now.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate endDate = now.with(TemporalAdjusters.lastDayOfMonth());
            Long start = startDate.atStartOfDay(zoneId).toEpochSecond() * 1000;
            Long end = endDate.atTime(LocalTime.MAX).atZone(zoneId).toEpochSecond() * 1000;
            return db.reportDao().getTotalExpenseThisMonth(start, end);
        }
        return db.reportDao().getTotalExpense();
    }
}
