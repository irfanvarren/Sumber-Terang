package com.irfanvarren.myapplication.Database;

import android.app.Application;

import com.irfanvarren.myapplication.Model.Debt;

import java.util.Date;

public class DebtRepository {
    private DebtDao mDebtDao;
    private AppDatabase db;

    public DebtRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mDebtDao = db.debtDao();
    }

    public long insert(Debt debt) {
        if (debt.getCreatedAt() == null) {
            debt.setCreatedAt(new Date());
        }
        debt.setUpdatedAt(new Date());
        long id = db.debtDao().insert(debt);
        return id;
    }

    public void update(Debt debt) {
        debt.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDebtDao.update(debt);
        });
    }

    public void delete(Debt debt) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mDebtDao.delete(debt);
        });
    }
}
