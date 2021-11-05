package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Model.Purchase;
import com.irfanvarren.myapplication.Model.Purchase;
import com.irfanvarren.myapplication.Model.PurchaseDetail;

import java.util.Date;
import java.util.List;

public class PurchaseRepository {
    private PurchaseDao mPurchaseDao;
    private LiveData<List<Purchase>> mAllPuchases;
    private AppDatabase db;

    public PurchaseRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mPurchaseDao = db.purchaseDao();
    }

    public long insert(Purchase purchase) {
        if(purchase.getCreatedAt() == null) {
            purchase.setCreatedAt(new Date());
        }
        purchase.setUpdatedAt(new Date());
        long id = db.purchaseDao().insert(purchase);
        return id;
    }

    public void insertAllDetails(PurchaseDetail... purchaseDetails){
        db.purchaseDetailDao().insertAll(purchaseDetails);
    }

    public void update(Purchase purchase){
        purchase.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPurchaseDao.update(purchase);
        });
    }

    public void delete(Purchase purchase){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mPurchaseDao.delete(purchase);
        });
    }

    public Integer getNo(){
        return mPurchaseDao.getNo();
    }

    public Double getLastBoughtPrice(Integer productId){
        Double price = db.purchaseDetailDao().getLastBoughtPrice(productId);
        if(price == null) {
            price = new Double(0);
        }
        return price;
    }
}
