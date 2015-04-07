package com.michaelt.databasetesting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HopsActivity extends Activity implements DialogInterface.OnClickListener {

    private SQLiteDatabase mDatabase;
    private DatabaseHelper mDBHelper;
    private Context mContext;
    private String[] mColumns = { mDBHelper.ID, DatabaseHelper.HopsTableInfo.NAME, DatabaseHelper.HopsTableInfo.ALPHA_ACID };
    private Cursor current;

    @InjectView(R.id.list_hops) ListView mListView;

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

        if (current==null) {
            mDBHelper = DatabaseHelper.getInstance(mContext);
            mDatabase = mDBHelper.getWritableDatabase();
            new LoadCursorTask().execute();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = new MenuInflater(mContext);
        inflater.inflate(R.menu.menu_hops, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.Add) {
            add();
            return true;
        }
        return this.onOptionsItemSelected(item);
    }

    private void add() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View addView=inflater.inflate(R.layout.add_edit_hops, null);
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);

        builder.setTitle(R.string.add_hops_title).setView(addView)
                .setPositiveButton(R.string.ok, this)
                .setNegativeButton(R.string.cancel, null).show();
    }

    /**
     * This method will be invoked when a button in the dialog is clicked.
     *
     * @param di The dialog that received the click.
     * @param whichButton  The button that was clicked (e.g.
     *               {@link android.content.DialogInterface#BUTTON1}) or the position
     */
    @Override
    public void onClick(DialogInterface di, int whichButton) {
        ContentValues values=new ContentValues(2);
        AlertDialog dlg=(AlertDialog)di;
        EditText hopName=(EditText)dlg.findViewById(R.id.edit_hop_name);
        EditText alphaAcid=(EditText)dlg.findViewById(R.id.edit_alpha_acid);

        String name = hopName.getText().toString();
        String acid = alphaAcid.getText().toString();

        insertHops(name, acid);
    }

    public void insertHops(String name, String acid) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.HopsTableInfo.NAME, name);
        values.put(DatabaseHelper.HopsTableInfo.ALPHA_ACID, acid);
        new InsertTask().execute(values);
        //mDatabase.insert(DatabaseHelper.HopsTableInfo.TABLE_NAME, null, values);
    }

    public void close() {
        CursorAdapter mCAdapter = ((CursorAdapter)mListView.getAdapter());
        mCAdapter.getCursor().close();
        mDatabase.close();
    }

    public void deleteHops(String hopsName) {
        mDatabase.delete(DatabaseHelper.HopsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME + " = " + hopsName, null);
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
                                                            DatabaseHelper.ID,
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

//    private class InsertTask extends AsyncTask<ContentValues, Void, Cursor> {
//
//         /**
//         * Override this method to perform a computation on a background thread. The
//         * specified parameters are the parameters passed to {@link #execute}
//         * by the caller of this task.
//         * <p/>
//         * This method can call {@link #publishProgress} to publish updates
//         * on the UI thread.
//         *
//         * @param params The parameters of the task.
//         * @return A result, defined by the subclass of this task.
//         * @see #onPreExecute()
//         * @see #onPostExecute
//         * @see #publishProgress
//         */
//        @Override
//        protected Cursor doInBackground(ContentValues... params) {
//            mDatabase.insert(DatabaseHelper.HopsTableInfo.TABLE_NAME, DatabaseHelper.HopsTableInfo.NAME, params[0]);
//            return doQuery();
//        }
//
//        @Override
//        public void onPostExecute(Cursor result) {
//            ((CursorAdapter)mListView.getAdapter()).changeCursor(result);
//            current = result;
//        }
//
//        protected Cursor doQuery() {
//            Cursor result=
//                    mDBHelper.getReadableDatabase().query
//                                                    (
//                                                        DatabaseHelper.HopsTableInfo.TABLE_NAME,            //table
//                                                        new String[]                                        //columns
//                                                        {
//                                                            DatabaseHelper.ID,
//                                                            DatabaseHelper.HopsTableInfo.NAME,
//                                                            DatabaseHelper.HopsTableInfo.ALPHA_ACID
//                                                        },
//                                                        null,                                               //selection
//                                                        null,                                               //selectionArgs
//                                                        null,                                               //groupBy
//                                                        null,                                               //having
//                                                        DatabaseHelper.HopsTableInfo.NAME                   //orderBy
//                                                    );
//            result.getCount();
//            return(result);
//        }
//    }
}
