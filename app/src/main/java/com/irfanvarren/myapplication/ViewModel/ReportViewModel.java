package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.ReportRepository;
import com.irfanvarren.myapplication.Model.Report;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {
    private ReportRepository mRepository;
    //private final LiveData<List<Report>> mAllReportSync;

    public ReportViewModel (Application application) {
        super(application);
        mRepository = new ReportRepository(application);

    }

    public LiveData<List<Report>> getThisMonth(){
        return mRepository.getThisMonth();
    }


}
