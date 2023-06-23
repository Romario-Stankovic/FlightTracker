package rs.ac.singidunum.madexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import rs.ac.singidunum.madexam.database.models.User;

public class UserDatabase extends DatabaseHelper{

    public UserDatabase(@Nullable Context context) {
        super(context);
    }

    // Creates a user in the database
    public boolean createUser(String name, String lastName, String username, String password, String dateOfBirth, String gender) {
        // Reference to the database
        SQLiteDatabase db = this.getWritableDatabase();
        // Content to put into the database
        ContentValues cv = new ContentValues();

        // If we don't have a reference to the database, return false
        if(db == null) {
            return false;
        }

        // Add the data
        cv.put("name", name);
        cv.put("last_name", lastName);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("date_of_birth", dateOfBirth);
        cv.put("gender", gender);

        // Insert the data into the database
        long result = db.insert("user", null, cv);

        // Insertion is successful if result is not -1
        return result != -1;

    }

    // Find a user by id
    public User findUserByID(int id) {

        // Query to be executed
        String query = "SELECT * FROM user WHERE user_id = ?";

        // Reference to the database
        SQLiteDatabase db = this.getReadableDatabase();

        // If we don't have a database reference, return null
        if(db == null) {
            return null;
        }

        // A cursor pointing to the data
        Cursor cursor = db.rawQuery(query, new String[]{""+id});

        // If the database did not return the cursor or there are not rows, return null
        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        // Move the cursor to the first row
        cursor.moveToFirst();

        // Map the data to a User model
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setUsername(cursor.getString(3));
        user.setPassword(cursor.getString(4));
        user.setDateOfBirth(cursor.getString(5));
        user.setGender(cursor.getString(6));

        // Return the user
        return user;

    }

    // Find a user by username
    public User findUserByUsername(String username) {

        // Query to be executed
        String query = "SELECT * FROM user WHERE username = ?";

        // Reference to the database
        SQLiteDatabase db = this.getReadableDatabase();

        // If we don't have a database reference, return null
        if(db == null) {
            return null;
        }

        // A cursor pointing to the data
        Cursor cursor = db.rawQuery(query, new String[]{username});

        // If the database did not return a cursor, or there is no data, return null
        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        // Move to the first line
        cursor.moveToFirst();

        // Map the data to User model
        User user = new User();
        user.setId(cursor.getInt(0));
        user.setName(cursor.getString(1));
        user.setLastName(cursor.getString(2));
        user.setUsername(cursor.getString(3));
        user.setPassword(cursor.getString(4));
        user.setDateOfBirth(cursor.getString(5));
        user.setGender(cursor.getString(6));

        // Return the user
        return user;

    }

}
