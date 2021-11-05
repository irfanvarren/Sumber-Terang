package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "payments")
public class Payment {
    @PrimaryKey(autoGenerate = true)
    public Integer id;

    @ColumnInfo(name="purchase_id")
    public Integer purchaseId;

    @ColumnInfo(name="order_id")
    public Integer orderId;

    @ColumnInfo(name="debt_id")
    public Integer debtId;

    @ColumnInfo(name="receivable_id")
    public Integer receivableId;

    @ColumnInfo(name="invoice_no")
    public String invoiceNo;

    @ColumnInfo(name="payment_no")
    public String paymentNo;

    @ColumnInfo(name="amount")
    public Double amount;

    @ColumnInfo(name="note")
    public String note;

    @ColumnInfo(name = "created_at",defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date createdAt;

    @ColumnInfo(name = "updated_at",defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date updatedAt;

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }

    public Integer getReceivableId() {
        return receivableId;
    }

    public void setReceivableId(Integer receivableId) {
        this.receivableId = receivableId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
