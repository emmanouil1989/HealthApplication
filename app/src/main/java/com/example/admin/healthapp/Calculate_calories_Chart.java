package com.example.admin.healthapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Calculate_calories_Chart extends ActionBarActivity {
    private MySQLLiteHelperCalculateCalories db;
    private int i= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_calories__chart);
        db = new MySQLLiteHelperCalculateCalories(this);
         /*  Two arraylist created to display the data into the chart*/
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        ArrayList<String> dbData = db.getAllConsumed();
        /* a loop to take the data from data base. The getALLBmi method return an ArryList with data
      * from the data base. The loop take the data from one ArrayList and add it to entries ArrayList
      * which is used to display the data into the Chart*/
        for (String name : dbData) {
            float test = Float.parseFloat(name);
            entries.add(new BarEntry(test,i++));
            labels.add("Calories Consumed by user");


        }





        /* New BarDataSet instance from a chart libray to display chart data*/
        BarDataSet dataset = new BarDataSet(entries, "Calories Consumed by user");

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        labels.add("Calories Consumed by user");

        BarChart chart = new BarChart(Calculate_calories_Chart.this);
        setContentView(chart);

        BarData data = new BarData(labels, dataset);
        chart.setData(data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculate_calories__chart, menu);
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
