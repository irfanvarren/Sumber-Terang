package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.io.Serializable;
@Entity(tableName = "receivables")
public class Receivable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name = "order_id")
    public Integer orderId;

    @ColumnInfo(name = "customer_id")
    public Integer customerId;

    @ColumnInfo(name = "amount", defaultValue = "0")
    public Double amount;

    @ColumnInfo(name = "amount_paid", defaultValue = "0")
    public Double amountPaid;

    @ColumnInfo(name = "due_date")
    @TypeConverters(DateConverter.class)
    public Date dueDate;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "status", defaultValue = "false")
    public Boolean status;

    @ColumnInfo(name="created_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date createdAt;

    @ColumnInfo(name="updated_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date updatedAt;


    public Receivable() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
