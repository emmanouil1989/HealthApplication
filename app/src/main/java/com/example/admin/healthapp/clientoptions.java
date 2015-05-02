package com.example.admin.healthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class clientoptions extends ActionBarActivity {

    private Button btnBmi,btnCalories,btnTarget,btnBurned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientoptions);

        btnBmi = (Button) findViewById(R.id.bmi);
        btnCalories = (Button) findViewById(R.id.calories);
        btnTarget = (Button) findViewById(R.id.target);
        btnBurned = (Button) findViewById(R.id.caloriesburned);

        btnBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bimIntent = new Intent(clientoptions.this,calculateBmi.class);
                clientoptions.this.startActivity(bimIntent);


            }
        });
        btnBurned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent burnedIntent = new Intent(clientoptions.this,Calories_Burned.class);
                clientoptions.this.startActivity(burnedIntent);
            }
        });


        btnCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent caloriesIntent = new Intent(clientoptions.this,foodCalories.class);
                clientoptions.this.startActivity(caloriesIntent);
                */
                Intent caloriesIntent = new Intent(clientoptions.this,calculate_calories.class);
                clientoptions.this.startActivity(caloriesIntent);

            }
        });

        btnTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_clientoptions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
