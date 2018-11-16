package com.cassianomenezes.mytransferwise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cassianomenezes.mytransferwise.entries.BeerResponse;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "beers_db";
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

    public BeerResponse getBeer(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS,
                " name = ?",
                new String[] { name },
                null,
                null,
                null,
                null);

        BeerResponse beerResponse = null;

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            beerResponse = new BeerResponse();
            beerResponse.setName(cursor.getString(1));
            beerResponse.setTagline(cursor.getString(2));
            beerResponse.setFirstBrewed(cursor.getString(3));
            beerResponse.setDescription(cursor.getString(4));
        }

        return beerResponse;
    }

    public void addBeer(BeerResponse beerResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, beerResponse.getName());
        values.put(KEY_TAGLINE, beerResponse.getTagline());
        values.put(KEY_FIRST_BREWED, beerResponse.getFirstBrewed());
        values.put(KEY_DESCRIPTION, beerResponse.getDescription());

        db.insert(TABLE_NAME,null, values);

        db.close();
    }

    public int updateBeer(BeerResponse beerResponse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, beerResponse.getName());
        values.put(KEY_TAGLINE, beerResponse.getTagline());
        values.put(KEY_FIRST_BREWED, beerResponse.getFirstBrewed());
        values.put(KEY_DESCRIPTION, beerResponse.getDescription());

        int i = db.update(TABLE_NAME, values, "id = ?", new String[] { beerResponse.getName() });

        db.close();

        return i;
    }

    public List<BeerResponse> getAllBeers() {

        List<BeerResponse> beerResponses = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BeerResponse beerResponse;

        if (cursor.moveToFirst()) {
            do {
                beerResponse = new BeerResponse();
                beerResponse.setName(cursor.getString(1));
                beerResponse.setTagline(cursor.getString(2));
                beerResponse.setFirstBrewed(cursor.getString(3));
                beerResponse.setDescription(cursor.getString(4));

                beerResponses.add(beerResponse);
            } while (cursor.moveToNext());
        }

        db.close();

        return beerResponses;
    }
}
