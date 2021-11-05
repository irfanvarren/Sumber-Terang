package com.irfanvarren.myapplication.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.irfanvarren.myapplication.Model.Order;
import com.irfanvarren.myapplication.Model.Order;
import com.irfanvarren.myapplication.Model.OrderDetail;

import java.util.Date;
import java.util.List;

public class OrderRepository {
    private OrderDao mOrderDao;
    private LiveData<List<Order>> mAllPuchases;
    private AppDatabase db;

    public OrderRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
    }

    public long insert(Order order) {
        if(order.getCreatedAt() == null) {
            order.setCreatedAt(new Date());
        }
        order.setUpdatedAt(new Date());
        long id = db.orderDao().insert(order);
        return id;
    }

    public void insertAllDetails(OrderDetail... orderDetails){
        db.orderDetailDao().insertAll(orderDetails);
    }

    public void update(Order order){
        order.setUpdatedAt(new Date());
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.update(order);
        });
    }

    public void delete(Order order){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.delete(order);
        });
    }

    public Integer getNo(){
        return mOrderDao.getNo();
    }

    public Double getLastSoldPrice(Integer productId){
        return db.orderDetailDao().getLastSoldPrice(productId);
    }
}
