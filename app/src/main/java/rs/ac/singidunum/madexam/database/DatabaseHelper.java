package rs.ac.singidunum.madexam.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

// Used for SQLite database connection and initialization
public class DatabaseHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "Database.db";

    // The version of the database
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called the first time a SQLite database needs to be created
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create user table statement
        String createUserTable = "CREATE TABLE user (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "last_name TEXT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "date_of_birth TEXT, " +
                "gender TEXT" +
                ");";

        // Create star table statement
        String createStarTable = "CREATE TABLE user_flight (" +
                "user_flight_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "flight_id INTEGER" +
                ");";

        // Execute SQL
        sqLiteDatabase.execSQL(createUserTable);
        sqLiteDatabase.execSQL(createStarTable);

    }

    // Called when the SQLite database version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // Drop tables if they exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user_flight");

        // Create a new database
        onCreate(sqLiteDatabase);

    }

}
