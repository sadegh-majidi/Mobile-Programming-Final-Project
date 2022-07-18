package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.OrderDao;
import edu.sharif.snappfoodminus.model.Order;

public class OrderRepository {
    private final OrderDao orderDao;
    private final LiveData<List<Order>> allOrders;

    public OrderRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.orderDao = database.orderDao();
        this.allOrders = orderDao.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return this.allOrders;
    }

    public LiveData<List<Order>> getOrdersByRestaurant(int restaurantId) {
        return this.orderDao.getOrdersByRestaurant(restaurantId);
    }

    public LiveData<List<Order>> getOrdersByUser(int userId) {
        return this.orderDao.getOrdersByUser(userId);
    }

    public LiveData<Order> getOrderById(long id) {
        return this.orderDao.getOrderById(id);
    }

    public void insertOrder(Order order) {
        DataBase.databaseWriteExecutor.execute(() -> this.orderDao.insertOrder(order));
    }

    public void updateOrder(Order order) {
        DataBase.databaseWriteExecutor.execute(() -> this.orderDao.updateOrder(order));
    }

    public void deleteOrder(Order order) {
        DataBase.databaseWriteExecutor.execute(() -> this.orderDao.deleteOrder(order));
    }

    public void deleteAllOrders() {
        DataBase.databaseWriteExecutor.execute(orderDao::deleteAllOrders);
    }
}
