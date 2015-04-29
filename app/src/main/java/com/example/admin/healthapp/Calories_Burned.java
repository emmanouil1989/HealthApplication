package com.example.admin.healthapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Calories_Burned extends ActionBarActivity {
    private Button btnCalc;
    private Spinner sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8;
    private EditText txtResults;
    private HashMap<String,String> exercise = new HashMap<String,String>();
    private ArrayList<String> times= new ArrayList<String>();
    private ArrayList<String> exerciseList= new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories__burned);
        btnCalc = (Button) findViewById(R.id.calculate);
        sp1 = (Spinner) findViewById(R.id.spinner);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp3 = (Spinner) findViewById(R.id.spinner3);
        sp4 = (Spinner) findViewById(R.id.spinner4);
        sp5 = (Spinner) findViewById(R.id.spinner5);
        sp6 = (Spinner) findViewById(R.id.spinner6);
        sp7 = (Spinner) findViewById(R.id.spinner7);
        sp8 = (Spinner) findViewById(R.id.spinner8);
        txtResults = (EditText) findViewById(R.id.results);
        populateSpinner();
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void populateSpinner()
    {

        times.add("0");
        times.add("5");
        times.add("10");
        times.add("15");
        times.add("20");
        times.add("25");
        times.add("30");
        times.add("35");
        times.add("40");
        times.add("45");
        times.add("50");
        times.add("55");
        times.add("60");

        ArrayAdapter<String> adapter =
         new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp2.setAdapter(adapter);
        sp4.setAdapter(adapter);
        sp6.setAdapter(adapter);
        sp8.setAdapter(adapter);


        exercise.put("Running","8");
        exercise.put("walking","8");
        exercise.put("Busketball","8");
        exercise.put("Football","8");


        Set set = exercise.entrySet();
        Iterator i = set.iterator();

        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            exerciseList.add(me.getKey().toString());
        }
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exerciseList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(adapter2);
        sp3.setAdapter(adapter2);
        sp5.setAdapter(adapter2);
        sp7.setAdapter(adapter2);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calories__burned, menu);
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
