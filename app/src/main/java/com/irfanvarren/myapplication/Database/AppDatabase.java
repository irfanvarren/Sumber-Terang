package com.irfanvarren.myapplication.Database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Order;
import com.irfanvarren.myapplication.Model.OrderDetail;
import com.irfanvarren.myapplication.Model.OtherCost;
import com.irfanvarren.myapplication.Model.Payment;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.Purchase;
import com.irfanvarren.myapplication.Model.PurchaseDetail;
import com.irfanvarren.myapplication.Model.Receivable;
import com.irfanvarren.myapplication.Model.Report;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint("RestrictedApi")
@Database(
        entities = {
                Product.class,
                Category.class,
                Inventory.class,
                Customer.class,
                Distributor.class,
                CartItem.class,
                Purchase.class, PurchaseDetail.class,
                Order.class, OrderDetail.class,
                Debt.class, Receivable.class,
                Payment.class,
                OtherCost.class,
                Report.class
        }, 
        version = 1,
        exportSchema = true
)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract InventoryDao inventoryDao();
    public abstract CustomerDao customerDao();
    public abstract DistributorDao distributorDao();
    public abstract CartItemDao cartItemDao();

    public abstract PurchaseDao purchaseDao();
    public abstract PurchaseDetailDao purchaseDetailDao();

    public abstract OrderDao orderDao();
    public abstract OrderDetailDao orderDetailDao();
    public abstract DebtDao debtDao();
    public abstract ReceivableDao receivableDao();

    public abstract PaymentDao paymentDao();

    public abstract ReportDao reportDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized(AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database").addMigrations().addCallback(sAppDatabaseCallback).allowMainThreadQueries().build();
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
                Product product = new Product(1,"Product A",0,15000);
                product.setCreatedAt(new Date());
                product.setUpdatedAt(new Date());
                productDao.insertAll(product);
            });

        }
    };


}


