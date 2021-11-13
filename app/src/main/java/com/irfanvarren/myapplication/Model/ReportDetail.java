package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Relation;
import androidx.room.PrimaryKey;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class ReportDetail implements Serializable {
 
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "purchase_id")
    public Integer purchaseId;

    @ColumnInfo(name = "order_id")
    public Integer orderId;

    @ColumnInfo(name = "total")
    public Double total;

    @ColumnInfo(name = "transaction_date")
    @TypeConverters({DateConverter.class})
    public Date transactionDate;

       
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getTotal() {
        return total;
    }

    
    public void setTotal(Double total) {
        this.total = total;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

}
