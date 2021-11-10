package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Model.Customer;

import java.util.Date;
import java.util.List;

public class CustomerRepository {
    private CustomerDao mCustomerDao;
    private LiveData<List<Customer>> mAllCustomers;
    private AppDatabase db;


    // Note that in order to unit test the AppRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public CustomerRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mCustomerDao = db.customerDao();
        mAllCustomers = mCustomerDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Customer>> getAll() {
        return mAllCustomers;
    }

    public Customer findById(int id){
        return db.customerDao().findById(id);
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public long insert(Customer customer) {
        long id = db.customerDao().insert(customer);
        return id;
    }

    public void update(Customer customer){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.update(customer);
        });
    }
    
    public void delete(Customer customer){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.delete(customer);
        });
    }
}
