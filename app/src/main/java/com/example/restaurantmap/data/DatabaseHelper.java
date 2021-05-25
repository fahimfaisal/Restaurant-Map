package com.example.restaurantmap.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import com.example.restaurantmap.model.Locations;
import com.example.restaurantmap.util.Util;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FILE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Util.TITLE + " TEXT , " + Util.LATITUDE + " DOUBLE , " + Util.LONGITUDE + " DOUBLE);";
        db.execSQL(CREATE_FILE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS";
        db.execSQL(DROP_USER_TABLE, new String[]{Util.TABLE_NAME});
    }

    public long insertLocation(Locations locations) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.TITLE, locations.getTitle());
        contentValues.put(Util.LATITUDE, locations.getLatitude());
        contentValues.put(Util.LONGITUDE, locations.getLongitude());
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
        return newRowId;
    }


    public ArrayList<Locations> fetchAllLocations() {

        ArrayList<Locations> locationsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                Locations locations = new Locations();
                locations.setID(cursor.getInt(0));
                locations.setTitle(cursor.getString(1));
                locations.setLatitude(cursor.getDouble(2));
                locations.setLongitude(cursor.getDouble(3));

                locationsList.add(locations);

            } while (cursor.moveToNext());
        }
        db.close();
        return locationsList;
    }
}