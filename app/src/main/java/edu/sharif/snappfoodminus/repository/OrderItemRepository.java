package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.OrderItemDao;
import edu.sharif.snappfoodminus.model.OrderItem;

public class OrderItemRepository {
    private final OrderItemDao orderItemDao;
    private final LiveData<List<OrderItem>> allOrderItems;

    public OrderItemRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.orderItemDao = database.orderItemDao();
        this.allOrderItems = orderItemDao.getAllOrderItems();
    }

    public LiveData<List<OrderItem>> getAllOrderItems() {
        return this.allOrderItems;
    }

    public LiveData<List<OrderItem>> getOrderItemsByOrder(long orderId) {
        return this.orderItemDao.getOrderItemsByOrder(orderId);
    }

    public LiveData<OrderItem> getOrderItemById(long id) {
        return this.orderItemDao.getOrderItemById(id);
    }

    public void insertOrderItem(OrderItem orderItem) {
        DataBase.databaseWriteExecutor.execute(() -> this.orderItemDao.insertOrderItem(orderItem));
    }

    public void updateOrderItem(OrderItem orderItem) {
        DataBase.databaseWriteExecutor.execute(() -> this.orderItemDao.updateOrderItem(orderItem));
    }

    public void deleteOrderItem(OrderItem orderItem) {
        DataBase.databaseWriteExecutor.execute(() -> this.orderItemDao.deleteOrderItem(orderItem));
    }

    public void deleteAllOrderItems() {
        DataBase.databaseWriteExecutor.execute(orderItemDao::deleteAllOrderItems);
    }
}
