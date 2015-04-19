package com.michaelt.databasetesting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.List;
import butterknife.ButterKnife;
import fragments.NameBeerFragment;

/**
 * Created by Michael on 4/9/2015.
 */
public class CreateRecipeActivity extends Activity {

    private DatabaseHelper mDBHelper;
    private Context mContext;
    private FragmentTransaction ft;
    public List<String> mHopList, mYeastList, mGrainList;
    public String mBeerName, mBoilTime, mSpargeType, mMashType, mMashTemp, mYeastName;
    public int mPrimaryTime, mSecondaryCheck, mSecondaryTime;
    public String maltName1, maltName2, maltName3, maltName4, maltName5;
    public String hopName1, hopName2, hopName3, hopName4, hopName5, hopName6;
    public int timeHop1, timeHop2, timeHop3, timeHop4, timeHop5, timeHop6;

    private String[] columns = new String[] {
        "ROWID AS _id",
        DatabaseHelper.RecipesTableInfo.NAME,
        DatabaseHelper.RecipesTableInfo.BOIL_TIME,
        DatabaseHelper.RecipesTableInfo.MASH,
        DatabaseHelper.RecipesTableInfo.MASH_TEMP,
        DatabaseHelper.RecipesTableInfo.SPARGE,
        DatabaseHelper.RecipesTableInfo.FERMENT_TIME,
        DatabaseHelper.RecipesTableInfo.SECONDARY_FERMENT,
        DatabaseHelper.RecipesTableInfo.SECONDARY_FERMENT_TIME,
        DatabaseHelper.RecipesTableInfo.MALT_1,
        DatabaseHelper.RecipesTableInfo.MALT_2,
        DatabaseHelper.RecipesTableInfo.MALT_3,
        DatabaseHelper.RecipesTableInfo.MALT_4,
        DatabaseHelper.RecipesTableInfo.MALT_5,
        DatabaseHelper.RecipesTableInfo.HOPS_1,
        DatabaseHelper.RecipesTableInfo.HOPS_1_TIME,
        DatabaseHelper.RecipesTableInfo.HOPS_2,
        DatabaseHelper.RecipesTableInfo.HOPS_2_TIME,
        DatabaseHelper.RecipesTableInfo.HOPS_3,
        DatabaseHelper.RecipesTableInfo.HOPS_3_TIME,
        DatabaseHelper.RecipesTableInfo.HOPS_4,
        DatabaseHelper.RecipesTableInfo.HOPS_4_TIME,
        DatabaseHelper.RecipesTableInfo.HOPS_5,
        DatabaseHelper.RecipesTableInfo.HOPS_5_TIME,
        DatabaseHelper.RecipesTableInfo.HOPS_6,
        DatabaseHelper.RecipesTableInfo.HOPS_6_TIME,
        DatabaseHelper.RecipesTableInfo.YEAST };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        mContext = this;
        ButterKnife.inject(this);
        mDBHelper = DatabaseHelper.getInstance(mContext);

        //Retrieve all the relevant data from the database for creating a recipe
        mHopList = mDBHelper.getHopList();
        mHopList.add(0, "None");                    //Needs a "None" option, 0 as the defualt position
        mYeastList = mDBHelper.getYeastList();      //CANNOT have a "None" option, Yeast is required
        mGrainList = mDBHelper.getGrainList();
        mGrainList.add(0, "None");                  //Needs a "None" options, 0 as the defualt position

        ft = getFragmentManager().beginTransaction();
        final NameBeerFragment nameBeerFragment = new NameBeerFragment();
        ft.add(R.id.frame_container, nameBeerFragment);
        ft.commit();

    }

    /**
     * This method is called once the recipe creation process is finished.  All the data is saved
     * into ContentValues and a background ASYNC Task is created to handle the insertion.
     * Additional checks are needed currently.
     *
     * @param theFragment The current active fragment that launches this method,
     *                    currently YeastSelectionFragment
     */
    public void recipeCreated(Fragment theFragment) {
        ft = getFragmentManager().beginTransaction();
        ft.detach(theFragment);
        ContentValues cv = new ContentValues();
        cv.put(columns[1], mBeerName);
        cv.put(columns[2], mBoilTime);
        cv.put(columns[3], mMashType);
        cv.put(columns[4], mMashTemp);
        cv.put(columns[5], mSpargeType);
        cv.put(columns[6], mPrimaryTime);
        cv.put(columns[7], mSecondaryCheck);
        cv.put(columns[8], mSecondaryTime);
        cv.put(columns[9], maltName1);
        cv.put(columns[10], maltName2);
        cv.put(columns[11], maltName3);
        cv.put(columns[12], maltName4);
        cv.put(columns[13], maltName5);
        cv.put(columns[14], hopName1);
        cv.put(columns[15], timeHop1);
        cv.put(columns[16], hopName2);
        cv.put(columns[17], timeHop2);
        cv.put(columns[18], hopName3);
        cv.put(columns[19], timeHop3);
        cv.put(columns[20], hopName4);
        cv.put(columns[21], timeHop4);
        cv.put(columns[22], hopName5);
        cv.put(columns[23], timeHop5);
        cv.put(columns[24], hopName6);
        cv.put(columns[25], timeHop6);
        cv.put(columns[26], mYeastName);
        new InsertTask().execute(cv);
    }

    /**
     * All information is lost if the user hits the back button.  This launches a AlertDialog
     * so that the user can ensure that they want to lose all their progress in creating a new
     * recipe.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder
                .setTitle("Are you sure?")
                .setMessage("You will lose all your current recipe build's information.")
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateRecipeActivity.super.onBackPressed();
                    }
                })
                .show();
    }

    /**
     * Abstract class with a doQuery() method for returning an updated Cursor object to
     * the user.  When finished the MainActivity is launched.
     *
     * @param <T>
     */
    abstract private class BaseTask<T> extends AsyncTask<T, Void, Cursor> {
        @Override
        public void onPostExecute(Cursor result) {
            Intent i = new Intent(mContext, MainActivity.class);
            i.putExtra("RecipeAdded", true);
            startActivity(i);
        }

        protected Cursor doQuery() {
            Cursor result=
                    mDBHelper.getReadableDatabase().query
                            (
                                    DatabaseHelper.RecipesTableInfo.TABLE_NAME,  //table
                                    columns,                                     //columns
                                    null,                                        //selection
                                    null,                                        //selectionArgs
                                    null,                                        //groupBy
                                    null,                                        //having
                                    DatabaseHelper.ID                            //orderBy
                            );
            result.getCount();
            return(result);
        }
    }

    /**
     * Loads the current Cursor with an updated version after a deletion / insertion
     * on a background thread.
     */
    private class LoadCursorTask extends BaseTask<Void> {
        @Override
        protected Cursor doInBackground(Void... params) {
            return(doQuery());
        }
    }

    /**
     * Inserts a recipe into the database from the passed in Content Values
     * on a background thread.
     */
    private class InsertTask extends BaseTask<ContentValues> {
        @Override
        protected Cursor doInBackground(ContentValues... values) {
            mDBHelper.getWritableDatabase().insert
                    (
                            DatabaseHelper.RecipesTableInfo.TABLE_NAME,    //table
                            DatabaseHelper.RecipesTableInfo.NAME,          //nullColumnHack
                            values[0]                                   //values
                    );
            return(doQuery());
        }
    }

    /**
     * Deletes a recipe on a background thread based on the Recipe name.
     */
    private class DeleteTask extends BaseTask<String> {
        @Override
        protected Cursor doInBackground(String... name) {
            mDBHelper.getWritableDatabase().delete
                    (
                            DatabaseHelper.RecipesTableInfo.TABLE_NAME,
                            DatabaseHelper.RecipesTableInfo.NAME + " = ?",
                            name
                    );
            return(doQuery());
        }
    }
}
