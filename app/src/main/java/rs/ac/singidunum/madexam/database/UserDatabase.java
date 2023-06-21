package rs.ac.singidunum.madexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import rs.ac.singidunum.madexam.database.models.UserModel;

public class UserDatabase extends DatabaseHelper{

    public UserDatabase(@Nullable Context context) {
        super(context);
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

    public UserModel findUserByID(int id) {

        String query = "SELECT * FROM user WHERE user_id = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        if(db == null) {
            return null;
        }

        Cursor cursor = db.rawQuery(query, new String[]{""+id});

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        UserModel user = new UserModel();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setUsername(cursor.getString(3));
        user.setPassword(cursor.getString(4));
        user.setGender(cursor.getString(5));

        return user;

    }

    public UserModel findUserByUsername(String username) {

        String query = "SELECT * FROM user WHERE username = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if(db == null) {
            return null;
        }

        cursor = db.rawQuery(query, new String[]{username});

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        UserModel user = new UserModel();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setUsername(cursor.getString(3));
        user.setPassword(cursor.getString(4));
        user.setGender(cursor.getString(5));

        return user;

    }

}
