package com.irfanvarren.myapplication.Database;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Transaction;
import androidx.room.RawQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.irfanvarren.myapplication.Model.Report;
import java.util.List;

@Dao
public interface ReportDao {

    @RawQuery(observedEntities = Report.class)
    LiveData<List<Report>> getThisMonth(SupportSQLiteQuery query);
   
}
