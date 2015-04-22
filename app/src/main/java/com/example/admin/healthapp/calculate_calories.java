package com.example.admin.healthapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_calories);
        searchtxt =(EditText) findViewById(R.id.userinput);
        totaltxt = (EditText) findViewById(R.id.caloriestotal);

        btnSearch =(Button) findViewById(R.id.foodsearch);
        resultsview = (ListView) findViewById(R.id.foodresults);
       final ArrayAdapter  <String> adapter = new ArrayAdapter<String>(this
                ,android.R.layout.simple_list_item_1,itemmList);




        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String food = searchtxt.getText().toString();

                fetchJSON("http://api.nutritionix.com/v1_1/search/"+food+
                        "?fields=item_name%2Citem_id%2Cbrand_name%2Cnf_calories%" +
                        "2Cnf_total_fat&appId=57e2c990&appKey=127ef21aa96e931fdac63e5a3684a5f5");
                itemmList.clear();
                resultsview.setAdapter(adapter);
            }
        });



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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
