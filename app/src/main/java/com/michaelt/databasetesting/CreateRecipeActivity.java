package com.michaelt.databasetesting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Michael on 4/9/2015.
 */
public class CreateRecipeActivity extends Activity {

    List<String> mHopList, mYeastList, mGrainList;
    DatabaseHelper mDBHelper;
    Context mContext;

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
        loadSpinnerData();
    }

    //INJECT ALL THE SPINNERS!!!!!!!!!!!!!
    @InjectView(R.id.spinner_hops_1) Spinner mHopsSpinner1;
    @InjectView(R.id.spinner_hops_2) Spinner mHopsSpinner2;
    @InjectView(R.id.spinner_hops_3) Spinner mHopsSpinner3;
    @InjectView(R.id.spinner_hops_4) Spinner mHopsSpinner4;
    @InjectView(R.id.spinner_hops_5) Spinner mHopsSpinner5;
    @InjectView(R.id.spinner_hops_6) Spinner mHopsSpinner6;
    @InjectView(R.id.spinner_malt_1) Spinner mMaltSpinner1;
    @InjectView(R.id.spinner_malt_2) Spinner mMaltSpinner2;
    @InjectView(R.id.spinner_malt_3) Spinner mMaltSpinner3;
    @InjectView(R.id.spinner_malt_4) Spinner mMaltSpinner4;
    @InjectView(R.id.spinner_malt_5) Spinner mMaltSpinner5;
    @InjectView(R.id.spinner_yeast) Spinner mYeastSpinner;
    @InjectView(R.id.spinner_batch_or_fly) Spinner mBatchFlySpinner;
    @InjectView(R.id.spinner_secondary_ferment) Spinner mSecondarySpinner;
    @InjectView(R.id.spinner_mash) Spinner mMashSpinner;

    /**
     * Loads all the proper data into spinners for creating a recipe.
     */
    public void loadSpinnerData() {
        ArrayAdapter<String> hopDataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mHopList);
        ArrayAdapter<String> yeastDataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mYeastList);
        ArrayAdapter<String> maltDataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mGrainList);
        hopDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yeastDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maltDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mHopsSpinner1.setAdapter(hopDataAdapter);
        mHopsSpinner2.setAdapter(hopDataAdapter);
        mHopsSpinner3.setAdapter(hopDataAdapter);
        mHopsSpinner4.setAdapter(hopDataAdapter);
        mHopsSpinner5.setAdapter(hopDataAdapter);
        mHopsSpinner6.setAdapter(hopDataAdapter);

        mMaltSpinner1.setAdapter(maltDataAdapter);
        mMaltSpinner2.setAdapter(maltDataAdapter);
        mMaltSpinner3.setAdapter(maltDataAdapter);
        mMaltSpinner4.setAdapter(maltDataAdapter);
        mMaltSpinner5.setAdapter(maltDataAdapter);

        mYeastSpinner.setAdapter(yeastDataAdapter);

        List<String> batchOrSparge = new ArrayList<String>();
        batchOrSparge.add("Batch"); batchOrSparge.add("Fly"); batchOrSparge.add("Neither");

        List<String> trueOrFalse = new ArrayList<String>();
        trueOrFalse.add("True"); trueOrFalse.add("False");

        ArrayAdapter<String> trueOrFalseAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, trueOrFalse);
        ArrayAdapter<String> batchOrSpargeAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, batchOrSparge);
        trueOrFalseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        batchOrSpargeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mBatchFlySpinner.setAdapter(batchOrSpargeAdapter);
        mMashSpinner.setAdapter(trueOrFalseAdapter);
        mSecondarySpinner.setAdapter(trueOrFalseAdapter);
    }

    @OnClick(R.id.button_add_recipe)
    public void addRecipe(Button button) {

    }

}
