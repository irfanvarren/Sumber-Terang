package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.irfanvarren.myapplication.Database.OrderRepository;
import com.irfanvarren.myapplication.Database.PurchaseRepository;
import com.irfanvarren.myapplication.Model.Purchase;

public class OrderViewModel extends AndroidViewModel {
    private static OrderRepository mRepository;

    public OrderViewModel(Application application) {
        super(application);
        mRepository = new OrderRepository(application);
    }

    public Integer getNo() {
        return mRepository.getNo();
    }
}
