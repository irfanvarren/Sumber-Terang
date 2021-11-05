package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Product;

import java.util.List;

public class InventoryViewModel extends AndroidViewModel {
    private InventoryRepository mRepository;
    private final LiveData<List<Inventory>> mAllInventorySync;

    public InventoryViewModel (Application application) {
        super(application);
        mRepository = new InventoryRepository(application);
        mAllInventorySync = mRepository.getAll();
    }

    public LiveData<Double> getTotalAsset(int id){
        return mRepository.getTotalAsset(id);
    }

    public LiveData<List<Inventory>> getByProductId(int id){
        return mRepository.getByProductId(id);
    }

    public LiveData<List<Inventory>> getAll() { return mAllInventorySync; }

    public void insert(Inventory Inventory) { mRepository.insert(Inventory); }

}
