package com.example.admin.healthapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class calculate_calories extends ActionBarActivity  {
    private Button btnSearch;
    private EditText searchtxt,totaltxt;
    private ListView resultsview;
    private List<String> itemmList = new ArrayList<String>();
    private List<String> calorieslist = new ArrayList<String>();
    private double call1,total;
    private double call2;
    private  ArrayAdapter  <String> adapter;
    private MySQLLiteHelperCalculateCalories db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_calories);

        db = new MySQLLiteHelperCalculateCalories(this);
        searchtxt =(EditText) findViewById(R.id.userinput);
        totaltxt = (EditText) findViewById(R.id.caloriestotal);

        btnSearch =(Button) findViewById(R.id.foodsearch);
        resultsview = (ListView) findViewById(R.id.foodresults);


        adapter=   new ArrayAdapter<String>
                (calculate_calories.this,android.R.layout.simple_list_item_1,itemmList);
        resultsview.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 /* Search food from API and call showData method for display in listview*/
                itemmList.clear();
            String food = searchtxt.getText().toString();

                fetchJSON("http://api.nutritionix.com/v1_1/search/"+food+
                        "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%" +
                        "2Cnf_total_fat&appId=57e2c990&appKey=127ef21aa96e931fdac63e5a3684a5f5");


                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);







            }
        });


        /* ListView click event for calculation the total calories */

        resultsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                call1 = Double.parseDouble((String)calorieslist.get(position).toString());

                total = call2+call1;
              total=  (double)Math.round(total * 10) / 10 ;
                totaltxt.setText(String.valueOf(total));
                call2=Double.parseDouble(totaltxt.getText().toString().trim());


            }
        });




    }

    /* Display tha data from Arraylist to listView*/
    public void showData(){
        adapter=   new ArrayAdapter<String>
                (calculate_calories.this,android.R.layout.simple_list_item_1,itemmList);
        resultsview.setAdapter(adapter);

    }
    /* Get API data*/
    public void fetchJSON(final String urlString)
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setConnectTimeout(15000);
                    conn.setReadTimeout(10000);
                    conn.setRequestMethod("GET");
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    String data = getStringfromInputStream(inputStream);
                  //  Log.v("JSONExample", "Data: " +data);
                    inputStream.close();
                    conn.disconnect();
                    readAndParseJSON(data);


                }
                catch (Exception e)
                {

                }


            }
        });
        thread.start();
        try {
            thread.join();
        }
        catch (Exception e)
        {

        }
        showData();
    }


    public String getStringfromInputStream(InputStream is)
    {
        BufferedReader br=null;
        StringBuilder sb = new StringBuilder();
        String line;

        try
        {
            br = new BufferedReader(new InputStreamReader(is));
            while((line=br.readLine())!=null)
            {
                sb.append(line);
            }

        }
        catch (Exception e)
        {
            try
            {
                br.close();
            }
            catch (Exception e1)
            {

            }

        }
        return sb.toString();
    }

        /* Read data from API and add it to arrayList of Strings */
    public void readAndParseJSON(String in)
    {
        try
        {
            JSONObject reader = new JSONObject(in);



            JSONArray temp = reader.getJSONArray("hits");


            for(int i=0;i<temp.length();i++)
            {
                JSONObject c = temp.getJSONObject(i);




             //   Log.v("JSONExample", c.toString());
                findKeys(c);
                String fields = c.getString("fields");
             //  Log.v("JSONExample", "fields: " + fields);


                JSONObject d = c.getJSONObject("fields");
              //  findKeys(d);
                String itemName = d.getString("item_name");
                String itCalories = d.getString("nf_calories");
                String itBrandName = d.getString("brand_name");

                String allStrings = itemName + " - " + itBrandName + " - " + itCalories;

                itemmList.add(allStrings);
                calorieslist.add(itCalories);


                Log.v("JSONExample", "item_name " + itemName);
                Log.v("JSONExample", "nf_calories " + itCalories);

            }

        }
        catch (Exception e)
        {

        }

    }


    public void findKeys(JSONObject issueObj)
    {
        try
        {
            Iterator iterator = issueObj.keys();

            while(iterator.hasNext())
            {
                String key = (String) iterator.next();
                Log.v("JSONExample", "Key: " + key);
            }

        }
        catch (Exception e)
        {

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculate_calories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_delete)
        {
            db.deleteAllData();

        }
        else if(id==R.id.action_save)
        {
            if(totaltxt.getText().toString().length()>0)
            {
                String result = totaltxt.getText().toString().trim();
                db.insertConsumed(result);
                totaltxt.setText(" ");
                Toast.makeText(calculate_calories.this, "Data Data Inserted Successfully", Toast.LENGTH_LONG).show();

            }else {

                Toast.makeText(calculate_calories.this, "Please Enter a value", Toast.LENGTH_LONG).show();
            }

        }
        else if(id==R.id.action_show)
        {
            ArrayList<String> dbData = db.getAllConsumed();
            for (String name : dbData) {
                Toast.makeText(calculate_calories.this,name,Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(calculate_calories.this,"show",Toast.LENGTH_LONG).show();
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
