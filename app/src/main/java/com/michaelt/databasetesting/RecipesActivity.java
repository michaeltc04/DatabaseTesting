package com.michaelt.databasetesting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;


public class RecipesActivity extends Activity {

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDBHelper;
    private Context mContext;
    private String[] mColumns = {
                                    mDBHelper.ID, DatabaseHelper.RecipesTableInfo.NAME, DatabaseHelper.RecipesTableInfo.BOIL_TIME, DatabaseHelper.RecipesTableInfo.MASH,
                                    DatabaseHelper.RecipesTableInfo.MASH_TEMP, DatabaseHelper.RecipesTableInfo.SPARGE, DatabaseHelper.RecipesTableInfo.FERMENT_TIME,
                                    DatabaseHelper.RecipesTableInfo.SECONDARY_FERMENT, DatabaseHelper.RecipesTableInfo.SECONDARY_FERMENT_TIME, DatabaseHelper.RecipesTableInfo.MALT_1,
                                    DatabaseHelper.RecipesTableInfo.MALT_2, DatabaseHelper.RecipesTableInfo.MALT_3, DatabaseHelper.RecipesTableInfo.MALT_4,
                                    DatabaseHelper.RecipesTableInfo.MALT_5, DatabaseHelper.RecipesTableInfo.HOPS_1, DatabaseHelper.RecipesTableInfo.HOPS_1_TIME,
                                    DatabaseHelper.RecipesTableInfo.HOPS_2, DatabaseHelper.RecipesTableInfo.HOPS_2_TIME, DatabaseHelper.RecipesTableInfo.HOPS_3,
                                    DatabaseHelper.RecipesTableInfo.HOPS_3_TIME, DatabaseHelper.RecipesTableInfo.HOPS_4, DatabaseHelper.RecipesTableInfo.HOPS_4_TIME,
                                    DatabaseHelper.RecipesTableInfo.HOPS_5, DatabaseHelper.RecipesTableInfo.HOPS_5_TIME, DatabaseHelper.RecipesTableInfo.HOPS_6,
                                    DatabaseHelper.RecipesTableInfo.HOPS_6_TIME, DatabaseHelper.RecipesTableInfo.YEAST
                                };
    private Cursor current;

    @InjectView(R.id.list_recipes) ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        ButterKnife.inject(this);
        this.mContext = this;
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (
                        this,                                                       //Context
                        R.layout.recipes_row,                                       //Layout
                        current,                                                    //Cursor
                        new String[]                                                //Columns
                                    {
                                        DatabaseHelper.RecipesTableInfo.NAME
                                    },
                        new int[]                                                   //Column Views
                                {
                                        R.id.recipe_name,
                                },
                        0                                                           //Flags
                );
        mListView.setAdapter(adapter);

        if (current==null) {
            mDBHelper = DatabaseHelper.getInstance(mContext);
            mDatabase = mDBHelper.getWritableDatabase();
            new LoadCursorTask().execute();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.menu_recipes, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Add:
                /*
                Intent i = new Intent(this, CreateRecipeActivity.class);
                startActivity(i);
                */
                return true;
            case R.id.Delete:
                delete();
                return true;
            default:
                return false;
        }
    }

    public void delete() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View addView=inflater.inflate(R.layout.delete_recipes, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        builder.setTitle(R.string.delete_recipes_title).
                setView(addView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dlg = (AlertDialog) dialog;
                        EditText recipeName = (EditText) dlg.findViewById(R.id.edit_hop_name);
                        new DeleteTask().execute(recipeName.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();

    }

    abstract private class BaseTask<T> extends AsyncTask<T, Void, Cursor> {
        @Override
        public void onPostExecute(Cursor result) {
            CursorAdapter mCAdapter = ((CursorAdapter)mListView.getAdapter());
            mCAdapter.changeCursor(result);
        }

        protected Cursor doQuery() {
            Cursor result=
                    mDBHelper.getReadableDatabase().query
                            (
                                    DatabaseHelper.HopsTableInfo.TABLE_NAME,     //table
                                    mColumns,                                    //columns
//                                    new String[]                                 //columns
//                                            {
//                                                    "ROWID AS _id",
//                                                    DatabaseHelper.HopsTableInfo.NAME,
//                                                    DatabaseHelper.HopsTableInfo.ALPHA_ACID
//                                            },
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

    private class LoadCursorTask extends BaseTask<Void> {
        @Override
        protected Cursor doInBackground(Void... params) {
            return(doQuery());
        }
    }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDBHelper.onUpgrade(mDatabase, 1, 1);
    }
}
