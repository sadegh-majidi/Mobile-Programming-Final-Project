package edu.sharif.snappfoodminus.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.Order;
import edu.sharif.snappfoodminus.model.OrderItem;
import edu.sharif.snappfoodminus.model.Rating;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.model.Review;
import edu.sharif.snappfoodminus.model.Role;
import edu.sharif.snappfoodminus.model.User;


@Database(entities = {
        User.class,
        Category.class,
        Restaurant.class,
        Food.class,
        Order.class,
        OrderItem.class,
        Rating.class,
        Review.class
        // TODO: add more entities (Discount, ...)
}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public static DataBase database;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized DataBase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    DataBase.class,
                    "sf_minus_database"
            ).addCallback(callback).fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract RestaurantDao restaurantDao();

    public abstract FoodDao foodDao();

    public abstract OrderDao orderDao();

    public abstract OrderItemDao orderItemDao();

    public abstract ReviewDao reviewDao();

    public abstract RatingDao ratingDao();

    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                UserDao userDao = database.userDao();
                userDao.deleteAllUsers(); //TODO: check that this task is not running every time.
                userDao.insertUser(new User("admin", "admin", Role.ADMIN, "Default System Admin"));
            });
        }
    };

}