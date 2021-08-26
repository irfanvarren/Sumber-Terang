package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;

import java.util.List;

public class ProductRepository {
    private ProductDao mProductDao;
    private CategoryDao mCategoryDao;
    private LiveData<List<Product>> mAllProducts;
    private LiveData<List<Category>> mAllCategories;
    private LiveData<List<ProductAndCategory>> mAllProductAndCategories;


    // Note that in order to unit test the AppRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mProductDao = db.productDao();
        mAllProducts = mProductDao.getAll();
        mCategoryDao = db.categoryDao();
        mAllCategories = mCategoryDao.getAll();
        mAllProductAndCategories = mProductDao.getProductAndCategories();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Product>> getAllProduct() {
        return mAllProducts;
    }

    public LiveData<List<ProductAndCategory>> getAllProductAndCategory(){ return mAllProductAndCategories; }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertProduct(Product product) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.insertAll(product);
        });
    }

    public void updateProduct(Product product){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.updateAll(product);
        });
    }

    public LiveData<List<Category>> getAllCategory(){ return mAllCategories;}

    public void insertCategory(Category category) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCategoryDao.insertAll(category);
        });
    }


}


