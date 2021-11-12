package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.irfanvarren.myapplication.Model.Payment;

import java.util.Date;
import java.util.List;
public class PaymentRepository {
    private PaymentDao mPaymentDao;
    private AppDatabase db;

    public PaymentRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mPaymentDao = db.paymentDao();
    }


    public LiveData<List<Payment>> getAllDebtPayment(Integer id){
        return mPaymentDao.getAllDebtPayment(id);
    }

    public LiveData<List<Payment>> getAllReceivablePayment(Integer id){
        return mPaymentDao.getAllReceivablePayment(id);
    }
    

    public Double getDebtAmountPaidTotal(Integer id){
        return mPaymentDao.getDebtAmountPaidTotal(id);
    }

    public Double getReceivableAmountPaidTotal(Integer id){
        return mPaymentDao.getReceivableAmountPaidTotal(id);
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
