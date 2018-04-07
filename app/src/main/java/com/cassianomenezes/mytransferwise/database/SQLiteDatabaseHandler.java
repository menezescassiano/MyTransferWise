package com.cassianomenezes.mytransferwise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cassianomenezes.mytransferwise.entries.Beer;
import com.cassianomenezes.mytransferwise.entries.Player;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "players_db";
    private static final String TABLE_NAME = "beers";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_TAGLINE = "tagline";
    private static final String KEY_FIRST_BREWED = "jersey_number";
    private static final String KEY_DESCRIPTION = "birth_date";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_TAGLINE, KEY_FIRST_BREWED, KEY_DESCRIPTION};

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_TAGLINE + " TEXT, "
                + KEY_FIRST_BREWED + " TEXT,"
                + KEY_DESCRIPTION + " TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public Beer getBeer(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                " name = ?",
                new String[] { name },
                null,
                null,
                null,
                null);

        Beer beer = null;

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            beer = new Beer();
            beer.setName(cursor.getString(1));
            beer.setTagline(cursor.getString(2));
            beer.setFirstBrewed(cursor.getString(3));
            beer.setDescription(cursor.getString(4));
        }

        return beer;
    }

    public void addBeer(Beer beer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, beer.getName());
        values.put(KEY_TAGLINE, beer.getTagline());
        values.put(KEY_FIRST_BREWED, beer.getFirstBrewed());
        values.put(KEY_DESCRIPTION, beer.getDescription());

        db.insert(TABLE_NAME,null, values);

        db.close();
    }

    public int updateBeer(Beer beer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, beer.getName());
        values.put(KEY_TAGLINE, beer.getTagline());
        values.put(KEY_FIRST_BREWED, beer.getFirstBrewed());
        values.put(KEY_DESCRIPTION, beer.getDescription());

        int i = db.update(TABLE_NAME, values, "id = ?", new String[] { beer.getName() });

        db.close();

        return i;
    }

    public List<Beer> getAllBeers() {

        List<Beer> beers = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Beer beer;

        if (cursor.moveToFirst()) {
            do {
                beer = new Beer();
                beer.setName(cursor.getString(1));
                beer.setTagline(cursor.getString(2));
                beer.setFirstBrewed(cursor.getString(3));
                beer.setDescription(cursor.getString(4));

                beers.add(beer);
            } while (cursor.moveToNext());
        }

        db.close();

        return beers;
    }
}
