package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Model.Distributor;

import java.util.List;

public class DistributorRepository {
    private DistributorDao mDistributorDao;
    private LiveData<List<Distributor>> mAllDistributors;
    private AppDatabase db;


    // Note that in order to unit test the AppRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public DistributorRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mDistributorDao = db.distributorDao();
        mAllDistributors = mDistributorDao.getAll();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Distributor>> getAll() {
        return mAllDistributors;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public long insert(Distributor distributor) {
        long id = db.distributorDao().insert(distributor);
        return id;
    }

    public void update(Distributor distributor){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDistributorDao.update(distributor);
        });
    }

    public void delete(Distributor distributor){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDistributorDao.delete(distributor);
        });
    }
}
