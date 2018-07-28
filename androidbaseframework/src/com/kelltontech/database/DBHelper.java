/*
 *
 *  Proprietary and confidential. Property of Kellton Tech Solutions Ltd. Do not disclose or distribute.
 *  You must have written permission from Kellton Tech Solutions Ltd. to use this code.
 *
 */

package com.kelltontech.database;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author kapil.vij
 */
public class DBHelper extends SQLiteOpenHelper {

    private String LOG_TAG = "BaseSqliteOpenHelper";


    //	public final static String  DB_COLUMN_ID 							= "id";
//	public static final int     DB_OPERATION_FAILURE 					= -1;
//	public static final int     DB_OPERATION_SUCCESS 					= 1;
    private SQLiteDatabase mWritableDatabase;
    /**
     * Array of Table create queries...
     */
    static final ArrayList<String> DB_SQL_CREATE_TABLE_QUERIES = new ArrayList<>();

    /**
     * Array of Table drop queries...
     */
    static final ArrayList<String> DB_SQL_DROP_TABLE_QUERIES = new ArrayList<>();
    static final ArrayList<String> DB_SQL_UPGARDE_QUERIES = new ArrayList<>();

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(LOG_TAG, "Tables creation start.");
        for (String table : DB_SQL_CREATE_TABLE_QUERIES) {
            db.execSQL(table);
        }
        Log.i(LOG_TAG, "Tables creation end.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.i(LOG_TAG, "DB upgrade.");

        for (String table : DB_SQL_DROP_TABLE_QUERIES) {
            db.execSQL(table);
        }

        for (String table : DB_SQL_UPGARDE_QUERIES) {
            db.execSQL(table);
        }

        onCreate(db);
    }

    /**
     * Get the database instance.
     *
     * @return mWritableDatabase
     */
    public SQLiteDatabase getWritableDbInstance() {
        if (mWritableDatabase == null) {
            mWritableDatabase = this.getWritableDatabase();
        }
        return mWritableDatabase;
    }

}
