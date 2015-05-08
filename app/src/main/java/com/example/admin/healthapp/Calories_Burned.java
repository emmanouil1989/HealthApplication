package com.example.admin.healthapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Calories_Burned extends ActionBarActivity   {
    private Button btnCalc;
    private Spinner sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8;
    private EditText txtResults,txtWeight;
    private HashMap<String,String> exercise = new HashMap<String,String>();
    private ArrayList<String> times= new ArrayList<String>();
    private ArrayList<String> exerciseList= new ArrayList<String>();
    private String min1,min2,min3,min4,min5,min6,min7,min8;
    private double time1,time2,time3,time4,time5,time6,time7,time8,totaltime,totalmets;
    private double mets1,mets2,mets3,mets4,mets5,mets6,mets7,mets8;
    private static double value = 0.0175;
    private static double totalCaloriesBurned = 0;
    private MySQLLiteHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calories__burned);

        db = new MySQLLiteHelper(this);


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
        txtWeight = (EditText) findViewById(R.id.userweight);

        populateSpinner();





        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* Take user input data and call totalCaloriesBurned method to calculate user burned calories
             *  */


                if (txtWeight.getText().toString().length()==0){

                    Toast.makeText(Calories_Burned.this, "Please give your weight",
                            Toast.LENGTH_LONG).show();

                }else {
                    double userWeight = Double.parseDouble(txtWeight.getText().toString().trim());

                    time1 = Double.parseDouble(min2);
                    time2 = Double.parseDouble(min4);
                    time3 = Double.parseDouble(min6);
                    time4 = Double.parseDouble(min8);
                    /* mets varialible is equal with the key-pair value from hashmap */
                    mets1 = Double.parseDouble(exercise.get(min1));
                    mets2 = Double.parseDouble(exercise.get(min3));
                    mets3 = Double.parseDouble(exercise.get(min5));
                    mets4 = Double.parseDouble(exercise.get(min7));

                    totalCaloriesBurned= caloriesPerMinute(mets1,userWeight,time1)+
                    caloriesPerMinute(mets2,userWeight,time2)+caloriesPerMinute(mets3,userWeight,time3)
                    +caloriesPerMinute(mets4,userWeight,time4);

                    DecimalFormat form = new DecimalFormat("0.00");
                    txtResults.setText(form.format(totalCaloriesBurned));

                }

            /* take user spinner choise and pass to a string varialiable */
            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min1 = exerciseList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min2 = times.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min3 = exerciseList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min4 = times.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min5 = exerciseList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min6 = times.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min7 = exerciseList.get(position).toString();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                min8 = times.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
     
    }

    public void populateSpinner()
    {
        /* Values added to arraylist and hashmap  */
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
        /* The arrayList added to a listView*/
        ArrayAdapter<String> adapter =
         new ArrayAdapter<String>(this,  R.layout.spinner_item_text, times);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp2.setAdapter(adapter);
        sp4.setAdapter(adapter);
        sp6.setAdapter(adapter);
        sp8.setAdapter(adapter);



        exercise.put("Running","7");
        exercise.put("Swimming","10");
        exercise.put("Volleyball","8");
        exercise.put("Beach Volley","8");
        exercise.put("Gymnastics","4");
        exercise.put("Weight lifting","6");
        exercise.put("Aerobic","6.5");
        exercise.put("Bicycling","8");
        exercise.put("Rowing","12");
        exercise.put("Archery","3.5");
        exercise.put("Cricket","5");
        exercise.put("Tennis","7");
        exercise.put("Horseback","4");
        exercise.put("Judo","10");
        exercise.put("Polo","8");
        exercise.put("Boxing","12");
        exercise.put("Rugby","10");
        exercise.put("walking","7");
        exercise.put("Basketball","8");
        exercise.put("Football","9");

    /* Iterate throw hashMap to take key from hasmap and added to arrayList. */
        Set set = exercise.entrySet();
        Iterator i = set.iterator();

        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            exerciseList.add(me.getKey().toString());
        }
        /* the arrayList with hashMap values is used to display into listView*/
        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(this, R.layout.spinner_item_text, exerciseList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp1.setAdapter(adapter2);
        sp3.setAdapter(adapter2);
        sp5.setAdapter(adapter2);
        sp7.setAdapter(adapter2);


    }


    public static double caloriesPerMinute(double met, double weight, double time) {
        return value * met * weight * time;
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
        /*Option menu to call database class methods to insert,delete data. And new activity called
         * to display data into a chart */
        if(id == R.id.action_delete)
        {
            db.deleteAllData();

        }
        else if(id==R.id.action_save)
        {
            if(txtResults.getText().toString().length()>0)
            {
                String result = txtResults.getText().toString().trim();
                db.insertBunred(result);
                txtResults.setText(" ");
                Toast.makeText(Calories_Burned.this,"Data Data Inserted Successfully",Toast.LENGTH_LONG).show();

            }else {

                Toast.makeText(Calories_Burned.this, "Please Enter a value", Toast.LENGTH_LONG).show();
            }

        }
        else if(id==R.id.action_show)
        {

            Intent CalBurnedChart = new Intent(Calories_Burned.this,Calories_Burned_Chart.class);
            Calories_Burned.this.startActivity(CalBurnedChart);

        }


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
