package rs.ac.singidunum.madexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import rs.ac.singidunum.madexam.database.models.UserFlight;

public class UserFlightDatabase extends DatabaseHelper{

    public UserFlightDatabase(@Nullable Context context) {
        super(context);
    }

    // Create a row linking a user and a flight
    public boolean createUserFlight(int userId, int flightId) {

        // Get database instance
        SQLiteDatabase db = this.getWritableDatabase();
        // Content to put in the database
        ContentValues cv = new ContentValues();

        // If we don't have a reference to the database, return false
        if(db == null) {
            return false;
        }

        // Add the fields and data
        cv.put("user_id", userId);
        cv.put("flight_id", flightId);

        // Execute the insert and return the row ID
        long result = db.insert("user_flight", null, cv);

        // Insertion was successful if row is not -1
        return result != -1;

    }

    // Return a UserFlight by userId and flightId
    public UserFlight getUserFlightForUserAndFlight(int userId, int flightId) {

        // The query that should be executed
        String query = "SELECT * FROM user_flight WHERE user_id = ? AND flight_id = ?";

        // Instance of the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Return null if there is no database instance
        if(db == null) {
            return null;
        }

        // A cursor that points to the current row
        Cursor cursor = db.rawQuery(query, new String[]{""+userId, ""+flightId});

        // If the database did not return the cursor or rows, return null
        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        // Move to the first row
        cursor.moveToFirst();

        // Convert the cursor data to UserFlight model
        UserFlight userFlight = new UserFlight();
        userFlight.setId(cursor.getInt(0));
        userFlight.setUserId(cursor.getInt(1));
        userFlight.setFlightId(cursor.getInt(2));

        // return userFlight
        return userFlight;

    }

    public List<UserFlight> getUserFlightForUser(int userId) {

        // The query that should be executed
        String query = "SELECT * FROM user_flight WHERE user_id = ?";

        // A reference to the database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // If the database does not exist, return null
        if(db == null) {
            return null;
        }

        // A cursor pointing to the data
        Cursor cursor = db.rawQuery(query, new String[]{""+userId});

        // If the database did not return null, or 0 rows, return an empty list
        if(cursor == null || cursor.getCount() == 0) {
            return new ArrayList<>();
        }

        // A list of user flights
        List<UserFlight> userFlights = new ArrayList<>();

        // Foreach row, create a model and add it to the list
        while(cursor.moveToNext()) {
            UserFlight userFlight = new UserFlight();
            userFlight.setId(cursor.getInt(0));
            userFlight.setUserId(cursor.getInt(1));
            userFlight.setFlightId(cursor.getInt(2));
            userFlights.add(userFlight);
        }

        // Return the list
        return userFlights;

    }

    // Delete the user flight by its ID
    public boolean deleteUserFlight(int id) {

        // Reference to the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Execute the delete query and return the number of affected rows
        int result = db.delete("user_flight", "user_flight_id = ?", new String[]{""+id});

        // Deletion was successful if the result is greater than 0
        return result > 0;

    }

}
