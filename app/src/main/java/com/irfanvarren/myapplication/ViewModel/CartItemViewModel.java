package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irfanvarren.myapplication.Database.CartItemRepository;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;

import java.util.List;

public class CartItemViewModel extends AndroidViewModel {
    private MutableLiveData<List<CartItem>> cartItemList;

    private Integer totalQty = new Integer(0);
    private Double totalPrice = new Double(0);

    public CartItemViewModel(Application application) {
        super(application);
    }
    public void setData(MutableLiveData<List<CartItem>> cartItemsLive) {
        this.cartItemList = cartItemsLive;

    }

    public MutableLiveData<List<CartItem>> getData(){
        return this.cartItemList;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }


}
