package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.irfanvarren.myapplication.Model.Receivable;

import java.util.Date;
import java.util.List;

public class ReceivableRepository {
    private ReceivableDao mReceivableDao;
    private AppDatabase db;
    private LiveData<List<Receivable>> mAllReceivables;

    public ReceivableRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mReceivableDao = db.receivableDao();
        mAllReceivables = mReceivableDao.getAll();
    }

   
    public LiveData<List<Receivable>> getAll() {
        return mAllReceivables;
    }

    public Double getTotalReceivable(){
        return mReceivableDao.getTotalReceivable();
    }

    public Integer getTotalTransaction(){
        return mReceivableDao.getTotalTransaction();
    }

    public long insert(Receivable receivable) {
        if (receivable.getCreatedAt() == null) {
            receivable.setCreatedAt(new Date());
        }
        receivable.setUpdatedAt(new Date());
        long id = db.receivableDao().insert(receivable);
        return id;
    }

    public void update(Receivable receivable) {
        receivable.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mReceivableDao.update(receivable);
        });
    }

    public void delete(Receivable receivable) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mReceivableDao.delete(receivable);
        });
    }
}
