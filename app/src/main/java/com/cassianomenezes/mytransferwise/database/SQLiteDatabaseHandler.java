package com.cassianomenezes.mytransferwise.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cassianomenezes.mytransferwise.entries.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "players_db";
    private static final String TABLE_NAME = "players";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POSITION = "position";
    private static final String KEY_JERSEY_NUMBER = "jersey_number";
    private static final String KEY_BIRTH_DATE = "birth_date";
    private static final String KEY_NATIONALITY = "nationality";
    private static final String KEY_CONTRACT = "contract";
    private static final String[] COLUMNS = { KEY_ID, KEY_NAME, KEY_POSITION, KEY_JERSEY_NUMBER, KEY_BIRTH_DATE, KEY_NATIONALITY, KEY_CONTRACT };

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "position TEXT, "
                + "jersey_number TEXT,"
                + "birth_date TEXT,"
                + "nationality TEXT,"
                + "contract TEXT)";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public Player getPlayer(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " name = ?", // c. selections
                new String[] { name }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        Player player = null;

        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            player = new Player();
            player.setName(cursor.getString(1));
            player.setPosition(cursor.getString(2));
            player.setJerseyNumber(Integer.parseInt(cursor.getString(3)));
            player.setDateOfBirth(cursor.getString(4));
            player.setNationality(cursor.getString(5));
            player.setContractUntil(cursor.getString(6));
        }

        return player;
    }

    public void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_POSITION, player.getPosition());
        values.put(KEY_JERSEY_NUMBER, player.getJerseyNumber());
        values.put(KEY_BIRTH_DATE, player.getDateOfBirth());
        values.put(KEY_NATIONALITY, player.getNationality());
        values.put(KEY_CONTRACT, player.getContractUntil());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_POSITION, player.getPosition());
        values.put(KEY_JERSEY_NUMBER, player.getJerseyNumber());
        values.put(KEY_BIRTH_DATE, player.getDateOfBirth());
        values.put(KEY_NATIONALITY, player.getNationality());
        values.put(KEY_CONTRACT, player.getContractUntil());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { player.getName() });

        db.close();

        return i;
    }

    public List<Player> getAllPlayers() {

        List<Player> players = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Player player = null;

        if (cursor.moveToFirst()) {
            do {
                player = new Player();
                player.setName(cursor.getString(1));
                player.setPosition(cursor.getString(2));
                player.setJerseyNumber(Integer.parseInt(cursor.getString(3)));
                player.setDateOfBirth(cursor.getString(4));
                player.setNationality(cursor.getString(5));
                player.setContractUntil(cursor.getString(6));
                players.add(player);
            } while (cursor.moveToNext());
        }

        return players;
    }
}
