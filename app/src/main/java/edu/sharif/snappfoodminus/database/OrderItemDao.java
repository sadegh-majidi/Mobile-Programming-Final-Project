package edu.sharif.snappfoodminus.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.sharif.snappfoodminus.Constants;
import edu.sharif.snappfoodminus.model.OrderItem;

@Dao
public interface OrderItemDao {
    @Query("SELECT * FROM " + Constants.ORDER_ITEM_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<OrderItem>> getAllOrderItems();

    @Query("SELECT * FROM " + Constants.ORDER_ITEM_TABLE_NAME + " WHERE order_id=:orderId")
    LiveData<List<OrderItem>> getOrderItemsByOrder(long orderId);

    @Query("SELECT * FROM " + Constants.ORDER_ITEM_TABLE_NAME + " WHERE id=:id")
    LiveData<OrderItem> getOrderItemById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrderItem(OrderItem orderItem);

    @Update
    void updateOrderItem(OrderItem orderItem);

    @Delete
    void deleteOrderItem(OrderItem orderItem);

    @Query("DELETE FROM " + Constants.ORDER_ITEM_TABLE_NAME)
    void deleteAllOrderItems();
}
