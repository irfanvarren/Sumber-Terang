package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.ReceivableRepository;
import com.irfanvarren.myapplication.Model.Receivable;

import java.util.List;

public class ReceivableViewModel extends AndroidViewModel {
    private ReceivableRepository mRepository;
    //private final LiveData<List<Receivable>> mAllReceivableSync;

    public ReceivableViewModel (Application application) {
        super(application);
        mRepository = new ReceivableRepository(application);

    }

    public LiveData<List<Receivable>> getAll(){
        return mRepository.getAll();
    }

    public LiveData<Double> getTotalReceivable(){
        return mRepository.getTotalReceivable();
    }

    public LiveData<Integer> getTotalTransaction(){
        return mRepository.getTotalTransaction();
    }


}
