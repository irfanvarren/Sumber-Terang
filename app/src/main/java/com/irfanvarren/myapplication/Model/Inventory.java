package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "inventory_managements")
public class Inventory implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "product_id")
    public int productId;

    @ColumnInfo(name = "distributor_id")
    public int distributorId;

    @ColumnInfo(name ="customer_id")
    public int customerId;

    @ColumnInfo(name="purchase_id")
    public int purchaseId;

    @ColumnInfo(name="order_id")
    public int orderId;

    @ColumnInfo(name = "invoice_no")
    public String invoiceNo;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "transaction_type")
    public String transactionType;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "qty")
    public int qty;

    @ColumnInfo(name = "note")
    public String note;

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date createdAt;

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateConverter.class)
    public Date updatedAt;

    public Inventory(int productId, int distributorId, String invoiceNo, String type, String transactionType, String productName, int qty, double price, String note) {
        this.productId = productId;
        if (distributorId != 0) {
            this.distributorId = distributorId;
        }
        if (invoiceNo != null) {
            this.invoiceNo = invoiceNo;
        }
        this.type = type;
        this.transactionType = transactionType;
        this.productName = productName;
        this.price = price;
        this.qty = qty;
        this.note = note;
    }

    @Ignore
    public Inventory(int productId, int distributorId, String invoiceNo, String type, String transactionType, String productName, int qty, double price) {
        this.productId = productId;
        if (distributorId != 0) {
            this.distributorId = distributorId;
        }
        if (invoiceNo != null) {
            this.invoiceNo = invoiceNo;
        }
        this.type = type;
        this.transactionType = transactionType;
        this.productName = productName;
        this.price = price;
        this.qty = qty;

    }


    @Ignore
    public Inventory() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(int distributorId) {
        this.distributorId = distributorId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
