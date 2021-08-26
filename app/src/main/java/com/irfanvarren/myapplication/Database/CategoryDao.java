package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Product;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAll();


    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category categories);

    @Query("DELETE FROM categories")
    void deleteAll();
}
