package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.PaymentRepository;
import com.irfanvarren.myapplication.Model.Payment;

import java.util.List;

public class PaymentViewModel extends AndroidViewModel {
    private PaymentRepository mRepository;
    //private final LiveData<List<Payment>> mAllPaymentSync;

    public PaymentViewModel (Application application) {
        super(application);
        mRepository = new PaymentRepository(application);

    }
    

    public LiveData<List<Payment>> getAllDebtPayment(Integer id){
        return mRepository.getAllDebtPayment(id);
    }

    public LiveData<List<Payment>> getAllReceivablePayment(Integer id){
        return mRepository.getAllReceivablePayment(id);
    }

}
