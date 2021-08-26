package com.irfanvarren.myapplication.Database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint("RestrictedApi")
@Database(
        entities = {Product.class,Category.class},
        version = 1,
        exportSchema = true
)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();

    public abstract CategoryDao categoryDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized(AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database").addCallback(sAppDatabaseCallback).build();
                }

            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sAppDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ProductDao productDao = INSTANCE.productDao();
                CategoryDao categoryDao = INSTANCE.categoryDao();
                productDao.deleteAll();
                Category category = new Category("Kategori Test");
                categoryDao.insertAll(category);
                Product product = new Product(1,"Product A",12,15000);
                productDao.insertAll(product);
            });

        }
    };


}


