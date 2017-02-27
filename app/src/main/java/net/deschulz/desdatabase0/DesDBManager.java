package net.deschulz.desdatabase0;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by schulz on 2/14/17.
 */

public class DesDBManager {
    public static final String DEBUG_TAG = "DesDBManager";

    private SQLiteDatabase db;
    private SQLiteOpenHelper desDBHelper;
    private boolean isOpen;


    /*  These agree with the columns in DesDBHelper.  Is there a way to import them directly?
        This seems error prone.
     */
    private static final String[] COLUMNS = {
            DesDBHelper.COLUMN_ID,
            DesDBHelper.COLUMN_NAME,
            DesDBHelper.COLUMN_PASSWORD
    };

    /* The constructor creates the database. */
    public DesDBManager(Context context) {
        this.desDBHelper = new DesDBHelper(context);
        isOpen = false;
    }

    public void open() {
        db = desDBHelper.getWritableDatabase();
        isOpen = true;
        Log.d(DEBUG_TAG, "desDBHelper opened");
    }

    public void close() {
        if (desDBHelper != null) {
            isOpen = false;
            desDBHelper.close();
            Log.d(DEBUG_TAG, "desDBbHelper closed");
        }

    }

    public long newEntryOld(String name, String password) {
        Log.i(DEBUG_TAG,"creating new entry: " + name + " " + password);
        ContentValues values = new ContentValues();
        values.put(DesDBHelper.COLUMN_NAME,name);
        values.put(DesDBHelper.COLUMN_PASSWORD,password);
        return db.insert(DesDBHelper.TABLE_PASSWORD,null,values);
    }

    public long newEntry(String name, String password) {
        if (!isOpen) {
            Log.i(DEBUG_TAG,"trying to insert into close database");
            return -1;
        }
        String sql = "INSERT INTO " + DesDBHelper.TABLE_PASSWORD
                + "(" + DesDBHelper.COLUMN_NAME + "," + DesDBHelper.COLUMN_PASSWORD + ")"
                + " VALUES (?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1,name);
        stmt.bindString(2,password);

        /* execute() and executeInsert() do not return cursors */
        return stmt.executeInsert();
    }

    public void delete(String name) {
        String whereClause = DesDBHelper.COLUMN_NAME + "=" + name;
    }

    public ArrayList<DesDbRecord> getAll() {
        ArrayList<DesDbRecord> dbRecs = new ArrayList<DesDbRecord>();
        Cursor cursor = null;
        try {
            cursor = db.query(DesDBHelper.TABLE_PASSWORD, COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    DesDbRecord card = new DesDbRecord();
                    card.setId(cursor.getLong(cursor.getColumnIndex(DesDBHelper.COLUMN_ID)));
                    card.setName(cursor.getString(cursor.getColumnIndex(DesDBHelper.COLUMN_NAME)));
                    card.setPassword(cursor.getString(cursor.getColumnIndex(DesDBHelper.COLUMN_PASSWORD)));
                    dbRecs.add(card);
                }
            }
            Log.d(DEBUG_TAG, "Total rows = " + cursor.getCount());
        } catch (Exception e){
            Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return dbRecs;
    }

    /* use parameter substitution to prevent sql injection attacks */
    public DesDbRecord getOneRec(String name) {
        String table = DesDBHelper.TABLE_PASSWORD;
        String[] columns = {DesDBHelper.COLUMN_NAME, DesDBHelper.TABLE_PASSWORD};
        String selection = DesDBHelper.COLUMN_NAME + " = ?";        //WHERE Clause
        String[] selArgs = { name };
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = null;
        try {
            cursor = db.query(table,columns,selection,selArgs,groupBy,having,orderBy);
            if (cursor.getCount() > 0) {
                if (cursor.moveToNext()) {
                    DesDbRecord retRec = null;
                    retRec =  new DesDbRecord();
                    retRec.setName(cursor.getString(cursor.getColumnIndex(DesDBHelper.COLUMN_NAME)));
                    retRec.setPassword(cursor.getString(cursor.getColumnIndex(DesDBHelper.COLUMN_PASSWORD)));
                    cursor.close();
                    return retRec;
                }
            }
            Log.d(DEBUG_TAG, "Total rows = " + cursor.getCount());
        } catch (Exception e){
            Log.d(DEBUG_TAG, "Exception raised with a value of " + e);
        } finally{
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
