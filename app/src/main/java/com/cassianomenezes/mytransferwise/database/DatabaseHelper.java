package com.cassianomenezes.mytransferwise.database;

import android.content.Context;

public class DatabaseHelper {

    private SQLiteDatabaseHandler database;

    public DatabaseHelper(Context context) {
        database = new SQLiteDatabaseHandler(context);
    }

    public SQLiteDatabaseHandler getDatabase() {
        return database;
    }
}
