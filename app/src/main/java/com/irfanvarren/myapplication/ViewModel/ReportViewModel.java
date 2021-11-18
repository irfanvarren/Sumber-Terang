package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.ReportRepository;
import com.irfanvarren.myapplication.Model.Report;
import com.irfanvarren.myapplication.Model.ReportDetail;
import java.util.List;
import java.util.Date;

public class ReportViewModel extends AndroidViewModel {
    private ReportRepository mRepository;
    //private final LiveData<List<Report>> mAllReportSync;

    public ReportViewModel (Application application) {
        super(application);
        mRepository = new ReportRepository(application);

    }

    public LiveData<List<ReportDetail>> getReportDetail(Date date) {
        return mRepository.getReportDetail(date);
    }

    public LiveData<List<Report>> getThisMonth(){
        return mRepository.getThisMonth();
    }

    public LiveData<List<Report>> getToday(){
        return mRepository.getToday();
    }

    public LiveData<List<Report>> getLastMonth(){
        return mRepository.getLastMonth();
    }

    public LiveData<Double> getTotalIncome(String durationType){
        return mRepository.getTotalIncome(durationType);
    }

    public LiveData<Double> getTotalExpense(String durationType){
        return mRepository.getTotalExpense(durationType);
    }


}
