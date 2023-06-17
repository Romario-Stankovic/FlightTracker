package rs.ac.singidunum.madexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Database.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createUserTable = "CREATE TABLE user (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "last_name TEXT, " +
                "username TEXT UNIQUE, " +
                "password TEXT, " +
                "date_of_birth TEXT, " +
                "gender TEXT" +
                ");";

        sqLiteDatabase.execSQL(createUserTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);

    }

}
