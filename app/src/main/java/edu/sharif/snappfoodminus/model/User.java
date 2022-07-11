package edu.sharif.snappfoodminus.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import edu.sharif.snappfoodminus.Constants;

@Entity(tableName = Constants.USER_TABLE_NAME)
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "username")
    @NonNull
    private String username;

    @ColumnInfo(name = "password")
    @NonNull
    private String password;

    @ColumnInfo(name = "role")
    @NonNull
    private Role role;

    @ColumnInfo(name = "name")
    private String name;

    public User(@NonNull String username, @NonNull String password, @NonNull Role role, String name) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public Role getRole() {
        return role;
    }

    public void setRole(@NonNull Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
