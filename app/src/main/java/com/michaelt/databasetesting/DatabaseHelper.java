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
import android.provider.ContactsContract;
import android.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipes.db";
    private static final int DATABSE_VERSION = 1;
    private SQLiteDatabase mDB;
    public static final String ID = "_id";
    private static DatabaseHelper sInstance;
    private boolean mResult;

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
        mDB = db;
        db.execSQL(HopsTableInfo.CREATE_HOPS_TABLE_QUERY);
        db.execSQL(YeastsTableInfo.CREATE_YEASTS_TABLE_QUERY);
        db.execSQL(GrainsTableInfo.CREATE_GRAINS_TABLE_QUERY);
        db.execSQL(RecipesTableInfo.CREATE_RECIPES_TABLE_QUERY);
        populateHops();
        populateYeasts();
        populateMalts();
        //populateRecipes();
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
        db.execSQL("DROP TABLE IF EXISTS " + RecipesTableInfo.TABLE_NAME);
        onCreate(db);
    }

    public static abstract class HopsTableInfo implements BaseColumns {
        public static final String TABLE_NAME = "Hops";
        public static final String NAME = "name";
        public static final String ALPHA_ACID= "alpha_acid";

        public static final String CREATE_HOPS_TABLE_QUERY =    "CREATE TABLE " + DatabaseHelper.HopsTableInfo.TABLE_NAME + "(" +
//                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
//                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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
//                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                DatabaseHelper.GrainsTableInfo.NAME + " TEXT, " +
                                                                DatabaseHelper.GrainsTableInfo.TYPE + " TEXT);";
    }

    public static abstract class RecipesTableInfo implements BaseColumns {
        public static final String TABLE_NAME = "Recipes";
        public static final String NAME = "name";
        public static final String BOIL_TIME = "boil_time";
        public static final String MASH = "mash";
        public static final String MASH_TEMP = "mash_temp";
        public static final String SPARGE = "sparge";
        public static final String FERMENT_TIME = "ferment_time_weeks";
        public static final String SECONDARY_FERMENT = "secondary_ferment";
        public static final String SECONDARY_FERMENT_TIME = "secondary_ferment_time_weeks";
        public static final String MALT_1 = "malt_1";
        public static final String MALT_2 = "malt_2";
        public static final String MALT_3 = "malt_3";
        public static final String MALT_4 = "malt_4";
        public static final String MALT_5 = "malt_5";
        public static final String HOPS_1 = "hops_1";
        public static final String HOPS_1_TIME = "hops_time_1";
        public static final String HOPS_2 = "hops_2";
        public static final String HOPS_2_TIME = "hops_time_2";
        public static final String HOPS_3 = "hops_3";
        public static final String HOPS_3_TIME = "hops_time_3";
        public static final String HOPS_4 = "hops_4";
        public static final String HOPS_4_TIME = "hops_time_4";
        public static final String HOPS_5 = "hops_5";
        public static final String HOPS_5_TIME = "hops_time_5";
        public static final String HOPS_6 = "hops_6";
        public static final String HOPS_6_TIME = "hops_time_6";
        public static final String YEAST = "yeast";

        public static final String CREATE_RECIPES_TABLE_QUERY =  "CREATE TABLE " + DatabaseHelper.RecipesTableInfo.TABLE_NAME + "(" +
//                                                                DatabaseHelper.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                                DatabaseHelper.RecipesTableInfo.NAME + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.BOIL_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.MASH + " INT, " +  //0 - false, 1 - true
                                                                DatabaseHelper.RecipesTableInfo.MASH_TEMP + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.SPARGE + " INT, " + //0 - false, 1 - true
                                                                DatabaseHelper.RecipesTableInfo.FERMENT_TIME + " REAL, " +
                                                                DatabaseHelper.RecipesTableInfo.SECONDARY_FERMENT + " INT, " +//0 - false, 1 - true
                                                                DatabaseHelper.RecipesTableInfo.SECONDARY_FERMENT_TIME + " REAL, " +
                                                                DatabaseHelper.RecipesTableInfo.MALT_1 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.MALT_2 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.MALT_3 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.MALT_4 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.MALT_5 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_1 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_1_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_2 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_2_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_3 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_3_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_4 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_4_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_5 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_5_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_6 + " TEXT, " +
                                                                DatabaseHelper.RecipesTableInfo.HOPS_6_TIME + " INT, " +
                                                                DatabaseHelper.RecipesTableInfo.YEAST + " TEXT);";
    }

    public boolean populateHops() {
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
            if (mDB != null) {
                mDB.beginTransaction();
                for(int i = 0; i < hopsArray.size(); i++) {
                    values.put(DatabaseHelper.HopsTableInfo.NAME, hopsArray.get(i).getName());
                    values.put(DatabaseHelper.HopsTableInfo.ALPHA_ACID, hopsArray.get(i).getAlphaAcid());
                    mDB.insert(DatabaseHelper.HopsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME, values);
                }
                mDB.setTransactionSuccessful();
                mResult = true;
            }
            return mResult;
        } finally {
            mDB.endTransaction();
        }
    }

    public boolean populateMalts() {
        ContentValues values = new ContentValues();
        ArrayList<Malts> maltsArray = new ArrayList<Malts>();
        Malts malt;
        malt = new Malts("2 Row", "Base");
        maltsArray.add(malt);
        malt = new Malts("Flaked Barley", "Adjunct/Flaked");
        maltsArray.add(malt);
        malt = new Malts("Caramel 60L", "Caramel/Crystal");
        maltsArray.add(malt);
        malt = new Malts("Honey Malt", "Caramel/Crystal");
        maltsArray.add(malt);
        malt = new Malts("Midnight Wheat", "Kilned/Toasted");
        maltsArray.add(malt);
        malt = new Malts("Chocolate", "Kilned/Toasted");
        maltsArray.add(malt);
        malt = new Malts("Golden Light", "DME");
        maltsArray.add(malt);
        malt = new Malts("Amber", "LME");
        maltsArray.add(malt);
        malt = new Malts("Sparkling Amber", "DME");
        maltsArray.add(malt);
        malt = new Malts("Pilsner", "Base");
        maltsArray.add(malt);
        malt = new Malts("White Wheat", "Base");
        maltsArray.add(malt);
        try {
            if (mDB != null) {
                mDB.beginTransaction();
                for(int i = 0; i < maltsArray.size(); i++) {
                    values.put(DatabaseHelper.GrainsTableInfo.NAME, maltsArray.get(i).getName());
                    values.put(DatabaseHelper.GrainsTableInfo.TYPE, maltsArray.get(i).getType());
                    mDB.insert(DatabaseHelper.GrainsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME, values);
                }
                mDB.setTransactionSuccessful();
                mResult = true;
            }
            return mResult;
        } finally {
            mDB.endTransaction();
        }
    }

    public boolean populateYeasts() {
        ContentValues values = new ContentValues();
        ArrayList<Yeasts> yeastsArray = new ArrayList<Yeasts>();
        Yeasts yeast;
        yeast = new Yeasts("WL008", "70-75", "Low-Med", "68-73");
        yeastsArray.add(yeast);
        yeast = new Yeasts("Wyeast 1388", "74-80", "Low", "64-80");
        yeastsArray.add(yeast);
        yeast = new Yeasts("WL380", "73-80", "Low", "66-70");
        yeastsArray.add(yeast);
        try {
            if (mDB != null) {
                mDB.beginTransaction();
                for(int i = 0; i < yeastsArray.size(); i++) {
                    values.put(DatabaseHelper.YeastsTableInfo.NAME, yeastsArray.get(i).getName());
                    values.put(DatabaseHelper.YeastsTableInfo.ATTENUATION, yeastsArray.get(i).getAttenuation());
                    values.put(DatabaseHelper.YeastsTableInfo.FLOCCULATION, yeastsArray.get(i).getFlocculation());
                    values.put(DatabaseHelper.YeastsTableInfo.FERMENTING_TEMPERATURE, yeastsArray.get(i).getFermentingTemperature());
                    mDB.insert(DatabaseHelper.YeastsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME, values);
                }
                mDB.setTransactionSuccessful();
                mResult = true;
            }
            return mResult;
        } finally {
            mDB.endTransaction();
        }
    }

    public List<String> getHopList() {
        List<String> hopList = new ArrayList<String>();
        String selectQuery = "SELECT " + DatabaseHelper.HopsTableInfo.NAME + " FROM " + DatabaseHelper.HopsTableInfo.TABLE_NAME + ";";
        Cursor cursor = getReadableDatabase().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                hopList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return hopList;
    }

    public List<String> getYeastList() {
        List<String> yeastList = new ArrayList<String>();
        String selectQuery = "SELECT " + DatabaseHelper.YeastsTableInfo.NAME + " FROM " + DatabaseHelper.YeastsTableInfo.TABLE_NAME + ";";
        Cursor cursor = getReadableDatabase().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                yeastList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return yeastList;
    }

    public List<String> getGrainList() {
        List<String> grainList = new ArrayList<String>();
        String selectQuery = "SELECT " + DatabaseHelper.GrainsTableInfo.NAME + " FROM " + DatabaseHelper.GrainsTableInfo.TABLE_NAME + ";";
        Cursor cursor = getReadableDatabase().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                grainList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();

        return grainList;
    }

    private class InsertTask extends AsyncTask<ContentValues, Void, Void> {

        private String mTableName;

        public InsertTask(String theTableName) {
            mTableName = theTableName;
        }

        @Override
        protected Void doInBackground(ContentValues... params) {
            switch (mTableName) {
                case HopsTableInfo.TABLE_NAME:
                    mDB.insert(mTableName, DatabaseHelper.HopsTableInfo.NAME, params[0]);
                    break;
                case YeastsTableInfo.TABLE_NAME:
                    mDB.insert(mTableName, DatabaseHelper.YeastsTableInfo.NAME, params[0]);
                    break;
                case GrainsTableInfo.TABLE_NAME:
                    mDB.insert(mTableName, DatabaseHelper.GrainsTableInfo.NAME, params[0]);
                    break;
                default:
                    break;
            }
            return null;
        }
    }
}