package rs.ac.singidunum.madexam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import rs.ac.singidunum.madexam.database.models.StarModel;

public class StarDatabase extends DatabaseHelper{

    public StarDatabase(@Nullable Context context) {
        super(context);
    }

    public boolean createStar(int userId, int flightId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("user_id", userId);
        cv.put("flight_id", flightId);

        long result = db.insert("star", null, cv);

        if(result == -1) {
            return false;
        }

        return true;

    }

    public StarModel getStarForUserAndFlight(int userId, int flightId) {

        String query = "SELECT * FROM star WHERE user_id = ? AND flight_id = ?";

        SQLiteDatabase db = this.getReadableDatabase();

        if(db == null) {
            return null;
        }

        Cursor cursor = db.rawQuery(query, new String[]{""+userId, ""+flightId});

        if(cursor == null || cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();

        StarModel star = new StarModel();
        star.setId(cursor.getInt(0));
        star.setUserId(cursor.getInt(1));
        star.setFlightId(cursor.getInt(2));

        return star;

    }

    public boolean deleteStar(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("star", "star_id = ?", new String[]{""+id});

        return result > 0;

    }

}
