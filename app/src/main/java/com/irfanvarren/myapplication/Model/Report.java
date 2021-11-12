package com.irfanvarren.myapplication.Model;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Report {
    @PrimaryKey
    public Integer id;

    @ColumnInfo(name = "total_transaction")
    public Integer totalTransaction;

    @ColumnInfo(name = "total_sell")
    public Double totalSell;

    @ColumnInfo(name = "total_purchase")
    public Double totalPurchase;

    @ColumnInfo(name = "transaction_date")
    @TypeConverters({DateConverter.class})
    public Date transactionDate;

       
    public void setTotalTransaction(Integer totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public Integer getTotalTransaction() {
        return totalTransaction;
    }

    public Double getTotalSell() {
        return totalSell;
    }

    
    public void setTotalSell(Double totalSell) {
        this.totalSell = totalSell;
    }

    public Double getTotalPurchase() {
        return totalPurchase;
    }

    
    public void setTotalPurchase(Double totalPurchase) {
        this.totalPurchase = totalPurchase;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

}
