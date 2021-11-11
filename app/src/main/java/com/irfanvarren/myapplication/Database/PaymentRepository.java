package com.irfanvarren.myapplication.Database;

import android.app.Application;

import com.irfanvarren.myapplication.Model.Payment;

import java.util.Date;

public class PaymentRepository {
    private PaymentDao mPaymentDao;
    private AppDatabase db;

    public PaymentRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mPaymentDao = db.paymentDao();
    }

    public Double getDebtAmountPaidTotal(Integer id){
        return mPaymentDao.getDebtAmountPaidTotal(id);
    }

    public long insert(Payment payment) {
        if (payment.getCreatedAt() == null) {
            payment.setCreatedAt(new Date());
        }

        if(payment.getPaymentDate() == null){
            payment.setPaymentDate(new Date());
        }
        
        payment.setUpdatedAt(new Date());
        long id = db.paymentDao().insert(payment);
        return id;
    }

    public void update(Payment payment) {
        payment.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPaymentDao.update(payment);
        });
    }

    public void delete(Payment payment) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPaymentDao.delete(payment);
        });
    }

    public Integer getNo() {
        return mPaymentDao.getNo();
    }
}
