package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.Model.Inventory;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {
    private ReportRepository mRepository;
    private final LiveData<List<Report>> mAllReportSync;

    public ReportViewModel (Application application) {
        super(application);
        mRepository = new ReportRepository(application);

    }

    public LiveData<Double> getTotalProfit(int id){
        return mRepository.getTotalProfit(id);
    }


    public LiveData<List<Report>> getAll(String type) {
        mAllReportSync = mRepository.getAll();
        return mAllReportSync;
    }

}
