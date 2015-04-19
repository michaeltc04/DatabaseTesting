package com.michaelt.databasetesting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        getActionBar().setTitle("Brewer's Guide");

        Intent i = getIntent();
        if (i != null) {
            if(i.getBooleanExtra("Recipe Added", false)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Recipe Added Successfully").setTitle("Recipe Added");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    @OnClick(R.id.button_recipes)
    public void onClickRecipes() {
        //Intent i = new Intent(this, RecipesActivity.class);
        Intent i = new Intent(this, CreateRecipeActivity.class);
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

//    public int lowest(int[] stock_prices_yesterday)
//    {
//        int[] arf = new int[] {5, 3, 2, 6, 7, 8, 2, 7, 9, 55, 1, 99, 100};
//        int[] arf = new int[] {15, 14, 13, 12, 11, 10, 9, 7, 5, 2, 1};
//        int result = lowest(arf);
//        System.out.println("" + arf);
//        boolean lowTest = false;
//        boolean highTest = false;
//        int max = 0;
//        int high = stock_prices_yesterday[0];
//        int low = stock_prices_yesterday[0];
//        for(int i = 1; i < stock_prices_yesterday.length ; i++)
//        {
//
//            if(stock_prices_yesterday[i] < low && lowTest && highTest) {
//                lowTest = false;
//                highTest = false;
//                max = high - low;
//                low = stock_prices_yesterday[i];
//                high = stock_prices_yesterday[i];
//            } else {
//                if (stock_prices_yesterday[i] < low)
//                {
//                    low = stock_prices_yesterday[i];
//                    lowTest = true;
//                }
//
//                if(stock_prices_yesterday[i] > high)
//                {
//                    high = stock_prices_yesterday[i];
//                    highTest = true;
//                }
//            }
//
//        }
//        int lastAmount = high - low;
//        if (lastAmount > max && max > 0) {
//            max = lastAmount;
//        }
//        return max;
//    }
}
