package rs.ac.singidunum.madexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
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

    public boolean createUser(String name, String lastName, String username, String password, String dateOfBirth, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("last_name", lastName);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("date_of_birth", dateOfBirth);
        cv.put("gender", gender);

        long result = db.insert("user", null, cv);

        if(result == -1) {
            return false;
        }

        return true;

    }

}
