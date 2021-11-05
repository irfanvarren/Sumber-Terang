package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.irfanvarren.myapplication.Database.OrderRepository;
import com.irfanvarren.myapplication.Database.PurchaseRepository;
import com.irfanvarren.myapplication.Model.Purchase;

public class PurchaseViewModel extends AndroidViewModel {
    private static PurchaseRepository mRepository;

    public PurchaseViewModel(Application application) {
        super(application);
        mRepository = new PurchaseRepository(application);
    }

    public Integer getNo() {
        return mRepository.getNo();
    }
}
