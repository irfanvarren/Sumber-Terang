package com.irfanvarren.myapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.irfanvarren.myapplication.Model.Distributor;

import java.util.List;

@Dao
public interface DistributorDao {
    @Query("SELECT * FROM distributors")
    LiveData<List<Distributor>> getAll();

    @Insert
    void insertAll(Distributor... distributors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Distributor distributor);

    @Update
    void update(Distributor distributor);

    @Delete
    void delete(Distributor distributor);

    @Query("DELETE FROM distributors")
    void deleteAll();
}
