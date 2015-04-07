package com.michaelt.databasetesting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {
//    @InjectView(R.id.button_recipes) Button mRecipeButton;
//    @InjectView(R.id.button_malts) Button mMaltsButton;
//    @InjectView(R.id.button_hops) Button mHopsButton;
//    @InjectView(R.id.button_yeasts) Button mYeastsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getActionBar().setTitle("Brewer's Guide");
    }

    @OnClick(R.id.button_recipes)
    public void onClickRecipes() {
        Intent i = new Intent(this, RecipesActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.button_malts)
    public void onClickMalts() {
        Intent i = new Intent(this, MaltsActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.button_hops)
    public void onClickHops() {
        Intent i = new Intent(this, HopsActivity.class);
        startActivity(i);
    }
    @OnClick(R.id.button_yeasts)
    public void onClickYeasts() {
        Intent i = new Intent(this, YeastsActivity.class);
        startActivity(i);
    }
}
