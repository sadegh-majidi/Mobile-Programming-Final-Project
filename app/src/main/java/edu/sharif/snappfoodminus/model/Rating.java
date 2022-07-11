package edu.sharif.snappfoodminus.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import edu.sharif.snappfoodminus.Constants;

@Entity(
        tableName = Constants.RATING_TABLE_NAME,
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = Food.class,
                        parentColumns = "id",
                        childColumns = "food_id",
                        onDelete = CASCADE)
        },
        indices = {
                @Index(value = {"user_id", "food_id"}, name = "unique_user_food_idx", unique = true)
        }
)
public class Rating {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "user_id")
    @NonNull
    private Integer userId;

    @ColumnInfo(name = "food_id")
    @NonNull
    private Integer foodId;

    @ColumnInfo(name = "rating")
    @NonNull
    private Double rating;

    public Rating(@NonNull Integer userId, @NonNull Integer foodId, @NonNull Double rating) {
        this.userId = userId;
        this.foodId = foodId;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(@NonNull Integer foodId) {
        this.foodId = foodId;
    }

    @NonNull
    public Double getRating() {
        return rating;
    }

    public void setRating(@NonNull Double rating) {
        this.rating = rating;
    }
}
