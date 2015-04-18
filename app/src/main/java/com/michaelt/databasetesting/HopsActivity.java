package com.michaelt.databasetesting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
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

import com.melnykov.fab.FloatingActionButton;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HopsActivity extends Activity {

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDBHelper;
    private Context mContext;
    private String[] mColumns = { mDBHelper.ID, DatabaseHelper.HopsTableInfo.NAME, DatabaseHelper.HopsTableInfo.ALPHA_ACID };
    private Cursor current;

    @InjectView(R.id.list_hops) ListView mListView;
    @InjectView(R.id.fab) FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hops);
        ButterKnife.inject(this);
        this.mContext = this;
        SimpleCursorAdapter adapter = new SimpleCursorAdapter
                                        (
                                            this,                                           //Context
                                            R.layout.hops_row,                              //Layout
                                            current,                                        //Cursor
                                            new String[]                                    //Columns
                                            {
                                                DatabaseHelper.HopsTableInfo.NAME,
                                                DatabaseHelper.HopsTableInfo.ALPHA_ACID
                                            },
                                            new int[]                                       //Column Views
                                            {
                                                R.id.hops_name,
                                                R.id.alpha_acid
                                            },
                                            0                                               //Flags
                                        );
        mListView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        if (current==null) {
            mDBHelper = DatabaseHelper.getInstance(mContext);
            mDatabase = mDBHelper.getWritableDatabase();
            new LoadCursorTask().execute();
        }
        fab.attachToListView(mListView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.menu_hops, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Add:
                add();
                return true;
            case R.id.Delete:
                deleteHops();
                return true;
            default:
                return false;
        }
    }

    private void add() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View addView=inflater.inflate(R.layout.add_hops, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        builder.setTitle(R.string.add_hops_title)
               .setView(addView)
               .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       AlertDialog dlg= (AlertDialog) dialog;
                       EditText hopName=(EditText)dlg.findViewById(R.id.edit_hop_name);
                       EditText alphaAcid=(EditText)dlg.findViewById(R.id.edit_alpha_acid);

                       ContentValues values = new ContentValues();
                       values.put(DatabaseHelper.HopsTableInfo.NAME, hopName.getText().toString());
                       values.put(DatabaseHelper.HopsTableInfo.ALPHA_ACID, alphaAcid.getText().toString());
                       new InsertTask().execute(values);
                   }
               })
               .setNegativeButton(R.string.cancel, null)
               .show();
    }

    public void close() {
        CursorAdapter mCAdapter = ((CursorAdapter)mListView.getAdapter());
        mCAdapter.getCursor().close();
        mDatabase.close();
    }

    public void deleteHops() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View addView=inflater.inflate(R.layout.delete_hops, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        builder.setTitle(R.string.delete_hops_title).
                setView(addView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog dlg = (AlertDialog) dialog;
                        EditText hopName = (EditText) dlg.findViewById(R.id.edit_hop_name);
                        new DeleteTask().execute(hopName.getText().toString());
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
                                                        new String[]                                 //columns
                                                        {
                                                            "ROWID AS _id",
                                                            DatabaseHelper.HopsTableInfo.NAME,
                                                            DatabaseHelper.HopsTableInfo.ALPHA_ACID
                                                        },
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

    private class InsertTask extends BaseTask<ContentValues> {
        @Override
        protected Cursor doInBackground(ContentValues... values) {
            mDBHelper.getWritableDatabase().insert
                                                (
                                                    DatabaseHelper.HopsTableInfo.TABLE_NAME,    //table
                                                    DatabaseHelper.HopsTableInfo.NAME,          //nullColumnHack
                                                    values[0]                                   //values
                                                );
            return(doQuery());
        }
    }

    private class DeleteTask extends BaseTask<String> {
        @Override
        protected Cursor doInBackground(String... name) {
            mDBHelper.getWritableDatabase().delete
                                                (
                                                  DatabaseHelper.HopsTableInfo.TABLE_NAME,
                                                  DatabaseHelper.HopsTableInfo.NAME + " = ?",
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
