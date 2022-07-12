package edu.sharif.snappfoodminus.model;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import edu.sharif.snappfoodminus.Constants;

@Entity(
        tableName = Constants.ORDER_ITEM_TABLE_NAME,
        foreignKeys = {
                @ForeignKey(entity = Order.class,
                        parentColumns = "id",
                        childColumns = "order_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = Food.class,
                        parentColumns = "id",
                        childColumns = "food_id",
                        onDelete = SET_NULL)
        },
        indices = {
                @Index(value = {"order_id"}, name = "order_id_idx"),
        }
)
public class OrderItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "order_id")
    @NonNull
    private Long orderId;

    @ColumnInfo(name = "food_id")
    private Integer foodId;

    @ColumnInfo(name = "qty")
    @NonNull
    private Integer quantity;

    public OrderItem(@NonNull Long orderId, Integer foodId, @NonNull Integer quantity) {
        this.orderId = orderId;
        this.foodId = foodId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(@NonNull Long orderId) {
        this.orderId = orderId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    @NonNull
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NonNull Integer quantity) {
        this.quantity = quantity;
    }
}
