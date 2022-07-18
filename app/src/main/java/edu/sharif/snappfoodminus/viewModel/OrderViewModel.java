package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.Order;
import edu.sharif.snappfoodminus.repository.OrderRepository;

public class OrderViewModel extends AndroidViewModel {
    private final OrderRepository orderRepository;
    private final LiveData<List<Order>> allOrders;

    public OrderViewModel(@NonNull Application application) {
        super(application);
        this.orderRepository = new OrderRepository(application);
        this.allOrders = this.orderRepository.getAllOrders();
    }

    public LiveData<List<Order>> getOrdersByUser(int userId) {
        return this.orderRepository.getOrdersByUser(userId);
    }

    public LiveData<List<Order>> getOrdersByRestaurant(int restaurantId) {
        return this.orderRepository.getOrdersByRestaurant(restaurantId);
    }

    public LiveData<Order> getOrderById(long id) {
        return this.orderRepository.getOrderById(id);
    }

    public void insertOrder(Order order) {
        this.orderRepository.insertOrder(order);
    }

    public void updateOrder(Order order) {
        this.orderRepository.updateOrder(order);
    }

    public void deleteOrder(Order order) {
        this.orderRepository.deleteOrder(order);
    }

    public void deleteAllOrders() {
        this.orderRepository.deleteAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return this.allOrders;
    }
}
