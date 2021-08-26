package com.irfanvarren.myapplication.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Product;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {
    private ProductRepository mRepository;

    private final LiveData<List<Category>> mAllCategoriesSync;
    private List<Category> mAllCategoriesAsync;

    public CategoryViewModel (Application application) {
        super(application);
        mRepository = new ProductRepository(application);
        mAllCategoriesSync = mRepository.getAllCategory();
    }

    public LiveData<List<Category>> getAll() { return mAllCategoriesSync; }

    public void insert(Category category) { mRepository.insertCategory(category); }

}
