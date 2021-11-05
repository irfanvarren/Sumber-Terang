package com.irfanvarren.myapplication.Model;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "cart_items")
public class CartItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="type")
    private int type;

    @Ignore
    public Product product;

    @Ignore
    private String name; //attribute for other type except product

    @ColumnInfo(name="purchase_id")
    private int purchaseId;

    @ColumnInfo(name="order_id")
    private int orderId;

    @ColumnInfo(name="product_id")
    private int productId;

    @ColumnInfo(name="qty", defaultValue = "0")
    private int qty;

    @ColumnInfo(name="price", defaultValue = "0")
    private Double price;

    /*
    type:
    1) Product
    2) Other Fee
     */

    public CartItem() {
    }

    public CartItem(int type, Product product) {
        this.type = type;
        this.product = product;
        this.productId = product.id;
    }

    public CartItem(int type, Product product, int qty, Double price) {
        this.type = type;
        this.product = product;
        this.productId = product.id;
        this.qty = qty;
        this.price = price;

    }

    public CartItem(int type, String name, int qty, Double price) {
        this.type = type;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
}
