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
        tableName = Constants.REVIEW_TABLE_NAME,
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
                @Index(value = {"food_id"}, name = "food_id_idx")
        }
)
public class Review {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "user_id")
    @NonNull
    private Integer userId;

    @ColumnInfo(name = "food_id")
    @NonNull
    private Integer foodId;

    @ColumnInfo(name = "review")
    @NonNull
    private String review;

    public Review(@NonNull Integer userId, @NonNull Integer foodId, @NonNull String review) {
        this.userId = userId;
        this.foodId = foodId;
        this.review = review;
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
    public String getReview() {
        return review;
    }

    public void setReview(@NonNull String review) {
        this.review = review;
    }
}
