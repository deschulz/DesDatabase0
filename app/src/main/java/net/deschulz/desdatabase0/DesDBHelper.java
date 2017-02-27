package net.deschulz.desdatabase0;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by schulz on 2/14/17.
 *
 * This is intended to be a generic template for use in other apps.   This app is
 * purely for demonstration purposes.
 */


public class DesDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "DesDBHelper";
    private static final String DB_NAME = "desDatabase.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_PASSWORD = "Password";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PASSWORD + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT" +
                    ")";

    public DesDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /* When we open the database (DesDBManager.open()) onCreate is called if the database
        does not exist.  If it does, then this isn't created.  What happens if there are several
        tables and some exist and others don't?
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(DEBUG_TAG,"DesDBHelper: OnCreate()");
        db.execSQL(TABLE_CREATE);
        Log.i(DEBUG_TAG, "Table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);
        onCreate(db);
    }
}