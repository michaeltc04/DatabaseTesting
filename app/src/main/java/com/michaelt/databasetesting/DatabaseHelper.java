/***
  Copyright (c) 2008-2012 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Android Development_
    http://commonsware.com/Android
*/

package com.michaelt.databasetesting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.widget.CursorAdapter;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABSE_VERSION = 1;
    public static final String ID = "_id";
    private static DatabaseHelper sInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HopsTableInfo.CREATE_HOPS_TABLE_QUERY);
        db.execSQL(YeastsTableInfo.CREATE_YEASTS_TABLE_QUERY);
        db.execSQL(GrainsTableInfo.CREATE_GRAINS_TABLE_QUERY);
        populateHops(db);

    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HopsTableInfo.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + YeastsTableInfo.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GrainsTableInfo.TABLE_NAME);
        onCreate(db);
    }

    public static abstract class HopsTableInfo implements BaseColumns {
        public static final String TABLE_NAME = "Hops";
        public static final String NAME = "name";
        public static final String ALPHA_ACID= "alpha_acid";

        public static final String CREATE_HOPS_TABLE_QUERY =    "CREATE TABLE " + DatabaseHelper.HopsTableInfo.TABLE_NAME + "(" +
                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                DatabaseHelper.HopsTableInfo.NAME + " TEXT, " +
                                                                DatabaseHelper.HopsTableInfo.ALPHA_ACID + " TEXT);";

    }

    public static abstract class YeastsTableInfo implements BaseColumns {
        public static final String TABLE_NAME = "Yeasts";
        public static final String NAME = "name";
        public static final String ATTENUATION = "attenuation";
        public static final String FLOCCULATION = "flocculation";
        public static final String FERMENTING_TEMPERATURE = "fermenting_temperature";

        public static final String CREATE_YEASTS_TABLE_QUERY =  "CREATE TABLE " + DatabaseHelper.YeastsTableInfo.TABLE_NAME + "(" +
                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                DatabaseHelper.YeastsTableInfo.NAME + " TEXT, " +
                                                                DatabaseHelper.YeastsTableInfo.ATTENUATION + " TEXT, " +
                                                                DatabaseHelper.YeastsTableInfo.FLOCCULATION + " TEXT, " +
                                                                DatabaseHelper.YeastsTableInfo.FERMENTING_TEMPERATURE + " TEXT);";
    }

    public static abstract class GrainsTableInfo implements BaseColumns {
        public static final String TABLE_NAME = "Grains";
        public static final String NAME = "name";
        public static final String TYPE = "type";

        public static final String CREATE_GRAINS_TABLE_QUERY =  "CREATE TABLE " + DatabaseHelper.GrainsTableInfo.TABLE_NAME + "(" +
                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                DatabaseHelper.GrainsTableInfo.NAME + " TEXT, " +
                                                                DatabaseHelper.GrainsTableInfo.TYPE + " TEXT);";
    }

    public boolean populateHops(SQLiteDatabase db) {
        boolean result = false;
        ContentValues values = new ContentValues();
        ArrayList<Hops> hopsArray = new ArrayList<Hops>();
        Hops hops;
        hops = new Hops("Brewer's Gold", "8-9%");
        hopsArray.add(hops);
        hops = new Hops("Bullion", "8-11%");
        hopsArray.add(hops);
        hops = new Hops("Centennial", "9-11.5%");
        hopsArray.add(hops);
        try {
            if (db != null) {
                db.beginTransaction();
                for (int i = 0; i < hopsArray.size(); i++) {
                    String name = hopsArray.get(i).getName();
                    String acid = hopsArray.get(i).getAlphaAcid();
                    values.put(DatabaseHelper.HopsTableInfo.NAME, name);
                    values.put(DatabaseHelper.HopsTableInfo.ALPHA_ACID, acid);
                    new InsertTask().execute(values);
                    values.clear();
                }
                db.setTransactionSuccessful();
                result = true;
            }
            return result;
        } finally {
            db.endTransaction();
        }
    }

    private class InsertTask extends AsyncTask<ContentValues, Void, Void> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(ContentValues... params) {
            sInstance.getWritableDatabase().insert(DatabaseHelper.HopsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME, params[0]);
            //getWritableDatabase().insert(DatabaseHelper.HopsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME, params[0]);
            return null;
        }

//        protected Cursor doQuery() {
//            Cursor result = getReadableDatabase().query
//                                                        (
//                                                                DatabaseHelper.HopsTableInfo.TABLE_NAME,            //table
//                                                                new String[]                                        //columns
//                                                                        {
//                                                                                DatabaseHelper.ID,
//                                                                                DatabaseHelper.HopsTableInfo.NAME,
//                                                                                DatabaseHelper.HopsTableInfo.ALPHA_ACID
//                                                                        },
//                                                                null,                                               //selection
//                                                                null,                                               //selectionArgs
//                                                                null,                                               //groupBy
//                                                                null,                                               //having
//                                                                DatabaseHelper.HopsTableInfo.NAME                   //orderBy
//                                                        );
//            result.getCount();
//            return(result);
//        }
    }
}