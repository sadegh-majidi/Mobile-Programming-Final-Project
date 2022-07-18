package edu.sharif.snappfoodminus.model;


import static androidx.room.ForeignKey.SET_NULL;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import edu.sharif.snappfoodminus.Constants;

@Entity(
        tableName = Constants.RESTAURANT_TABLE_NAME,
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "owner_id",
                        onDelete = SET_NULL
                )
        },
        indices = {
                @Index(value = {"owner_id"}, name = "restaurant_owner_id_idx")
        }
)
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;


    @ColumnInfo(name = "owner_id")
    private int ownerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public Restaurant(@NonNull String name, int ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }
}
