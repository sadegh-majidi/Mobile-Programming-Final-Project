//package edu.sharif.snappfoodminus.database;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import edu.sharif.snappfoodminus.model.Category;
//import edu.sharif.snappfoodminus.model.Food;
//import edu.sharif.snappfoodminus.model.Order;
//import edu.sharif.snappfoodminus.model.OrderItem;
//import edu.sharif.snappfoodminus.model.Rating;
//import edu.sharif.snappfoodminus.model.Restaurant;
//import edu.sharif.snappfoodminus.model.Review;
//import edu.sharif.snappfoodminus.model.User;
//
//
//@Database(entities = {
//        User.class,
//        Category.class,
//        Restaurant.class,
//        Food.class,
//        Order.class,
//        OrderItem.class,
//        Rating.class,
//        Review.class
//        // TODO: add more entities (Discount, ...)
//}, version = 1, exportSchema = false)
//public abstract class DataBase extends RoomDatabase {
//
//    public static DataBase database;
//
//    public static synchronized DataBase getInstance(Context context) {
//        if (database == null) {
//            database = Room.databaseBuilder(
//                    context,
//                    DataBase.class,
//                    "sf_minus_database"
//            ).addCallback(callback).fallbackToDestructiveMigration().build();
//        }
//        return database;
//    }
//
//    public abstract UserDao userDao();
//
//    public abstract CategoryDao categoryDao();
//
//    public abstract RestaurantDao restaurantDao();
//
//    public abstract FoodDao foodDao();
//
//    public abstract OrderDao orderDao();
//
//    public abstract OrderItemDao orderItemDao();
//
//    public abstract ReviewDao reviewDao();
//
//    public abstract RatingDao ratingDao();
//
//    private static final RoomDatabase.Callback callback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//            new PopulateDBTask(database).execute();
//        }
//    };
//
//    private static class PopulateDBTask extends AsyncTask<Void, Void, Void> {
//        private final UserDao userDao;
//
//        private PopulateDBTask(DataBase database) {
//            this.userDao = database.userDao();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            // TODO: insert admin here
//            return null;
//        }
//
//    }
//}