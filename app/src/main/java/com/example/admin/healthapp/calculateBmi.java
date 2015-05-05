package com.example.admin.healthapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;


public class calculateBmi extends ActionBarActivity {

    private EditText weighttxt,heighttxt,agetxt,displaytxt;
    private Button btnBmi;
    private float weight,height,result, age;
    private String bmiInterpetation ;
    private MySQLLiteHelperBmi db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate_bmi);


        db = new MySQLLiteHelperBmi(this);
        weighttxt = (EditText) findViewById(R.id.weightfield);
        heighttxt = (EditText) findViewById(R.id.heightfield);
        agetxt = (EditText) findViewById(R.id.agefield);

        btnBmi = (Button) findViewById(R.id.resultbtn);

        btnBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = Float.parseFloat(weighttxt.getText().toString().trim());
                height = Float.parseFloat(heighttxt.getText().toString().trim());
                age = Float.parseFloat(agetxt.getText().toString().trim());
                if(weighttxt.getText().toString().length() == 0 || heighttxt.getText().toString().length() == 0
                        ||agetxt.getText().toString().length() ==0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(calculateBmi.this);
                    builder.setTitle("Complete all the fields");
                    builder.setCancelable(false);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        }
                    });

                    AlertDialog alertbox  = builder.create();
                    alertbox.show();

                }else {


                    result = calculateResult(weight, height);
                    bmiInterpetation = interpretBMI(result, age);


                    AlertDialog.Builder builder1 = new AlertDialog.Builder(calculateBmi.this);
                    result=  (float)Math.round(result * 10) / 10 ;
                    builder1.setTitle(result + "-" + bmiInterpetation);
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

    }

    public float calculateResult (float weight,float height)
    {
        height = height /100;
        return (float) (weight / (height * height));
    }

    public String interpretBMI(float result,float age1)
    {

        if (age1 > 18)
        {
          if (result < 16)
          {
              return "Severely underweight";
          }
            else if(result <18)
          {
              return "underweight";
          }else if (result <24)
          {
              return "Normal weight";
          }else if(result <29)
            {
                return "Overweight";
            }else if (result < 35)
          {
              return "Seriously overweight";
          }else
          {
              return "Gravely overweight";
          }
        }else
        {
            return " You are under 18";
        }



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculate_bmi, menu);
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
            if(result == 0)
            {
                Toast.makeText(calculateBmi.this,"Please calculate BMI",Toast.LENGTH_LONG).show();
            }else
            {
                String res = String.valueOf(result);
                db.insertBmi(res);
                result = 0;
                Toast.makeText(calculateBmi.this, "Data Data Inserted Successfully", Toast.LENGTH_LONG).show();
            }
        }
        else if(id==R.id.action_show)
        {
            Intent BmiChart = new Intent(calculateBmi.this,Bmi_Chart.class);
            calculateBmi.this.startActivity(BmiChart);
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
