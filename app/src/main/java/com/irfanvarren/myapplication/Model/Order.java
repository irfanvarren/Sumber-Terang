package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="customer_id")
    public int customerId;

    @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="phone")
    public String Phone;

    @ColumnInfo(name="email")
    public String Email;

    @ColumnInfo(name="address")
    public String Address;

    @ColumnInfo(name="invoice_no")
    public String invoiceNo;

    @ColumnInfo(name="subtotal" ,defaultValue = "0")
    public Double subtotal;

    @ColumnInfo(name="other_cost" ,defaultValue = "0")
    public Double otherCost;

    @ColumnInfo(name="discount", defaultValue = "0")
    public Double discount;

    @ColumnInfo(name="discount_percent",defaultValue = "0")
    public Integer discountPercent;

    @ColumnInfo(name="total", defaultValue = "0")
    public Double total;

    @ColumnInfo(name="amount_paid",defaultValue = "0")
    public Double amountPaid;

    @ColumnInfo(name="note")
    public String note;

    @ColumnInfo(name="invoice_path")
    public String invoicePath;

    @ColumnInfo(name="status",defaultValue="0")
    public Boolean status;

    @ColumnInfo(name="transaction_date")
    @TypeConverters(DateConverter.class)
    public Date transactionDate;

    @ColumnInfo(name="due_date")
    @TypeConverters(DateConverter.class)
    public Date dueDate;

    @ColumnInfo(name="created_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date createdAt;

    @ColumnInfo(name="updated_at",defaultValue="CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date updatedAt;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getInvoicePath() {
        return invoicePath;
    }

    public void setInvoicePath(String invoicePath) {
        this.invoicePath = invoicePath;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
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
