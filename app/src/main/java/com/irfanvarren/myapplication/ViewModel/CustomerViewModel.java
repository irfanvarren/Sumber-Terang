package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.CustomerRepository;
import com.irfanvarren.myapplication.Model.Customer;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    private CustomerRepository mRepository;

    private final LiveData<List<Customer>> mAllCustomerSync;
    private List<Customer> mAllCustomerAsync;

    public CustomerViewModel (Application application) {
        super(application);
        mRepository = new CustomerRepository(application);
        mAllCustomerSync = mRepository.getAll();
    }

    public LiveData<List<Customer>> getAll() { return mAllCustomerSync; }

    public void insert(Customer category) { mRepository.insert(category); }
}
