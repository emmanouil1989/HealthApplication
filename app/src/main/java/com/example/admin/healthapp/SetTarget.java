package com.example.admin.healthapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class SetTarget extends ActionBarActivity {
    private EditText txtAge,txtHeight,txtWeight,txtTarget,txtdays;
    private Button btnCalculate;
    private Spinner spGender,spActivity;
    private double height,weight,target,activityFactor,bmrMale,bmrFemale,result,result2,result3,loosingCalories;
    private int days,age;
    private ArrayList<String> genderList  =new ArrayList<String>();
    private ArrayList<String>factorsList=new ArrayList<String>();
    private HashMap<String,String> activityList = new HashMap<String,String>();
    private String defineGender,factorValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_target);
         txtAge = (EditText) findViewById(R.id.age);
        txtHeight= (EditText) findViewById(R.id.height);
        txtWeight = (EditText) findViewById(R.id.weight);
        txtTarget = (EditText) findViewById(R.id.weightTarget);
        txtdays = (EditText) findViewById(R.id.days);
        btnCalculate =(Button) findViewById(R.id.calculate);
        spGender= (Spinner) findViewById(R.id.gender);
        spActivity = (Spinner) findViewById(R.id.dailyActivity);

        populateSpinners();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtAge.getText().toString().length()==0 || txtdays.getText().toString().length()== 0
                   ||txtTarget.getText().toString().length()==0 || txtWeight.getText().toString().length()==0
                   || txtHeight.getText().toString().length() == 0)
                {
                    Toast.makeText(SetTarget.this, "Please complete all the fields",
                            Toast.LENGTH_LONG).show();
                }else
                {
                    age = Integer.parseInt(txtAge.getText().toString().trim());
                     days= Integer.parseInt(txtdays.getText().toString().trim());
                    target = Double.parseDouble(txtTarget.getText().toString().trim());
                    weight = Double.parseDouble(txtWeight.getText().toString().trim());
                    height = Double.parseDouble(txtHeight.getText().toString().trim());

                    if(defineGender.equals("Male")) {
                     bmrMale=   maleBMR(age,weight,height)*activityFactor;
                        loosingCalories=(target*1000)/days;
                        result = bmrMale-loosingCalories ;
                        result=  (double)Math.round(result * 10) / 10 ;
                        result2 = loosingCalories * 0.6;
                        result2=  (double)Math.round(result2 * 10) / 10 ;
                        result3 = loosingCalories *0.4;
                        result3=  (double)Math.round(result3 * 10) / 10 ;
                    }else if(defineGender.equals("Female"))
                    {
                       bmrFemale= femaleBMR(age,weight,height)*activityFactor;
                        loosingCalories =(target*1000)/days;
                        result = bmrFemale- loosingCalories;
                        result=  (double)Math.round(result * 10) / 10 ;
                        result2 = loosingCalories * 0.6;
                        result2=  (double)Math.round(result2 * 10) / 10 ;
                        result3 = loosingCalories *0.4;
                        result3=  (double)Math.round(result3 * 10) / 10 ;
                    }



                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SetTarget.this);

                    String test = String.valueOf("Your daily calories should be " + result + "\n"
                    + "You should eat less " + result2 + " calories" +"\n"
                    +"You should burn " + result3 + " calories by exercising");
                    builder1.setMessage(test);
                    builder1.setCancelable(false);
                    builder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertbox1 = builder1.create();
                    alertbox1.show();
                }




            }
        });

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                defineGender = genderList.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 factorValue = factorsList.get(position).toString();
                activityFactor = Double.parseDouble(activityList.get(factorValue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void populateSpinners()
    {
        genderList.add("Male");
        genderList.add("Female");

        activityList.put("Sedentary (little or no exercise)", "1.2");
        activityList.put("Lightly Active (1-3 days per week)", "1.375");
        activityList.put("Moderately Active (3-5 days per week)", "1.55");
        activityList.put("ery Active ( 6 or 7 days per week)", "1.725");
        activityList.put("Extra Active ( hard training)", "1.9");

        Set set = activityList.entrySet();
        Iterator i = set.iterator();

        while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            factorsList.add(me.getKey().toString());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, factorsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spGender.setAdapter(adapter);
        spActivity.setAdapter(adapter2);

    }

    public double maleBMR(double age1,double weight1,double height1)
    {

        double result = 66 +(13.7 * weight1)+(5*height1)-(6.8 * age1);
        return result;
    }

    public double femaleBMR(double age2,double weight2,double height2)
    {
        double result = 655 +(9.6 * weight2)+(1.8*height2)-(4.7 * age2);
        return result;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_target, menu);
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
