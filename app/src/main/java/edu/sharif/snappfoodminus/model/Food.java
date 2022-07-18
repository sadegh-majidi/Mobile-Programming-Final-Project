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
        tableName = Constants.FOOD_TABLE_NAME,
        foreignKeys = {
                @ForeignKey(entity = Restaurant.class,
                parentColumns = "id",
                childColumns = "restaurant_id",
                onDelete = CASCADE),
                @ForeignKey(entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id",
                        onDelete = SET_NULL)
        },
        indices = {
                @Index(value = {"restaurant_id"}, name = "food_restaurant_id_idx"),
                @Index(value = {"category_id"}, name = "food_category_id_idx"),
        }
)
public class Food {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "restaurant_id")
    @NonNull
    private Integer restaurantId;

    @ColumnInfo(name = "category_id")
    private Integer categoryId;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "price")
    @NonNull
    private Integer price;

    public Food(@NonNull Integer restaurantId, Integer categoryId, @NonNull String name, @NonNull Integer price) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(@NonNull Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Integer getPrice() {
        return price;
    }

    public void setPrice(@NonNull Integer price) {
        this.price = price;
    }
}
