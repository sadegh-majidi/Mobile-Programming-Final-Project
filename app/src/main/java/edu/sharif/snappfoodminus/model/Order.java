package edu.sharif.snappfoodminus.model;

import static androidx.room.ForeignKey.SET_NULL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import edu.sharif.snappfoodminus.Constants;
import edu.sharif.snappfoodminus.utils.DateConverter;

@Entity(
        tableName = Constants.ORDER_TABLE_NAME,
        foreignKeys = {
                @ForeignKey(entity = Restaurant.class,
                        parentColumns = "id",
                        childColumns = "restaurant_id",
                        onDelete = SET_NULL),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = SET_NULL)
        },
        indices = {
                @Index(value = {"restaurant_id"}, name = "restaurant_id_idx"),
                @Index(value = {"user_id"}, name = "user_id_idx"),
        }
)
@TypeConverters(DateConverter.class)
public class Order {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "user_id")
    private Integer userId;

    @ColumnInfo(name = "restaurant_id")
    private Integer restaurantId;

    @ColumnInfo(name = "total_price")
    @NonNull
    private Long totalPrice;

    @ColumnInfo(name = "created_at")
    @NonNull
    private Date createdAt;

    public Order(Integer userId, Integer restaurantId, @NonNull Long totalPrice) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalPrice = totalPrice;
        this.createdAt = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @NonNull
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@NonNull Date createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(@NonNull Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
