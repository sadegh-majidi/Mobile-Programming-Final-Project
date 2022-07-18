package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.OrderItem;
import edu.sharif.snappfoodminus.repository.OrderItemRepository;

public class OrderItemViewModel extends AndroidViewModel {
    private final OrderItemRepository orderItemRepository;
    private final LiveData<List<OrderItem>> allOrderItems;

    public OrderItemViewModel(@NonNull Application application) {
        super(application);
        this.orderItemRepository = new OrderItemRepository(application);
        this.allOrderItems = this.orderItemRepository.getAllOrderItems();
    }

    public LiveData<List<OrderItem>> getOrderItemsByOrder(long orderId) {
        return this.orderItemRepository.getOrderItemsByOrder(orderId);
    }

    public LiveData<OrderItem> getOrderItemById(long id) {
        return this.orderItemRepository.getOrderItemById(id);
    }

    public void insertOrderItem(OrderItem orderItem) {
        this.orderItemRepository.insertOrderItem(orderItem);
    }

    public void updateOrderItem(OrderItem orderItem) {
        this.orderItemRepository.updateOrderItem(orderItem);
    }

    public void deleteOrderItem(OrderItem orderItem) {
        this.orderItemRepository.deleteOrderItem(orderItem);
    }

    public void deleteAllOrderItems() {
        this.orderItemRepository.deleteAllOrderItems();
    }

    public LiveData<List<OrderItem>> getAllOrderItems() {
        return this.allOrderItems;
    }
}
