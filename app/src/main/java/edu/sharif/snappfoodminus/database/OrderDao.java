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
import edu.sharif.snappfoodminus.model.Order;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM " + Constants.ORDER_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM " + Constants.ORDER_TABLE_NAME + " WHERE restaurant_id=:restaurantId")
    LiveData<List<Order>> getOrdersByRestaurant(int restaurantId);

    @Query("SELECT * FROM " + Constants.ORDER_TABLE_NAME + " WHERE user_id=:userId")
    LiveData<List<Order>> getOrdersByUser(int userId);

    @Query("SELECT * FROM " + Constants.ORDER_TABLE_NAME + " WHERE id=:id")
    LiveData<Order> getOrderById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("DELETE FROM " + Constants.ORDER_TABLE_NAME)
    void deleteAllOrders();
}
