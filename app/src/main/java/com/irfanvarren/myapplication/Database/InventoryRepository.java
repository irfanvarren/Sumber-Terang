package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Model.Inventory;

import java.util.Date;
import java.util.List;

public class InventoryRepository {
    private InventoryDao mInventoryDao;
    private LiveData<List<Inventory>> mAllInventorys;
    private AppDatabase db;


    // Note that in order to unit test the AppRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public InventoryRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mInventoryDao = db.inventoryDao();
        mAllInventorys = mInventoryDao.getAll();
     }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Inventory>> getAll() {
        return mAllInventorys;
    }

    public LiveData<List<Inventory>> getByProductId(int id){
        return db.inventoryDao().getByProductId(id);
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public LiveData<Double> getTotalAsset(int productId){
        return db.inventoryDao().getTotalAsset(productId);
    }

    public long insert(Inventory inventory) {
        if(inventory.getCreatedAt() == null) {
            inventory.setCreatedAt(new Date());
        }
        inventory.setUpdatedAt(new Date());
        long id = db.inventoryDao().insert(inventory);
        return id;
    }

    public void insertAll(Inventory... inventories) {
       db.inventoryDao().insertAll(inventories);
    }

    public void update(Inventory inventory){
        inventory.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mInventoryDao.update(inventory);
        });
    }

    public void updateProductName(int productId,String name){
        db.inventoryDao().updateProductName(productId,name);
    }

    public void delete(Inventory inventory){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mInventoryDao.delete(inventory);
        });
    }

    public void deleteByProductId(int id){
        db.inventoryDao().deleteByProductId(id);
    }
}
