package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.DistributorRepository;
import com.irfanvarren.myapplication.Model.Distributor;

import java.util.List;

public class DistributorViewModel extends AndroidViewModel {
    private DistributorRepository mRepository;

    private final LiveData<List<Distributor>> mAllDistributorSync;
    private List<Distributor> mAllDistributorAsync;

    public DistributorViewModel (Application application) {
        super(application);
        mRepository = new DistributorRepository(application);
        mAllDistributorSync = mRepository.getAll();
    }

    public LiveData<List<Distributor>> getAll() { return mAllDistributorSync; }

    public void insert(Distributor category) { mRepository.insert(category); }
}
