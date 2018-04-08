package com.example.maciek.wagi;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Maciek on 2017-11-13.
 */

public class Weight extends AppCompatActivity {
    private EditText weight;
    private Button date;
    private Button confirm;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog datePickerDialog;
    //private String regex = "(\\d{0,3}|\\d{0,3}\\.\\d?)$";

    public static class Filter implements InputFilter {
        private Matcher matcher;
        private String pattern;
        private String regex = "(\\d{0,3}|\\d{0,3}\\.\\d?)$";
        //private Pattern pattern;

        public Filter() {
            //pattern = Pattern.compile("^\\d+(\\.)?$");
            //pattern = Pattern.compile("\\d+(\\.)?$");

            //pattern = Pattern.compile("(\\.[1-9])||([1-9])||(\\.)||(\\d+\\.\\d)");
            //pattern = Pattern.compile("^(\\d+)(\\.\\d)$||\\d+");
            //pattern = Pattern.compile(regex);
            pattern = regex;
            //((\.\d{1,2})?)||(\.)?");
            //"[1-9][0-9]{0,2}+((\\.[0-9]?)?)||(\\.)?"
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //pattern = Pattern.compile("\\d+(\\.\\d{1,2})?");
            //matcher = pattern.matcher(source);
            //return result.matches(regex) ? null : "";
            /*if (!matcher.matches())
                return "";
            else
                return null;*/
            String destination = dest.toString();
            String result = destination.substring(0, dstart) + source.subSequence(start, end) + destination.substring(dend);
            return result.matches(regex) ? null : "";
        }
    }


    //Filter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        weight = (EditText) findViewById(R.id.weight);


        // DZIALALO JAKOS
        weight.setFilters(new InputFilter[] {new Filter()});

        /*weight.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String destination = dest.toString();
                        String result = destination.substring(0, dstart) + source.subSequence(start, end) + destination.substring(dend);
                        return result.matches(regex) ? null : "";
                        //return null;
                    }
                }
        });*/

        date = (Button) findViewById(R.id.date);
        confirm = (Button) findViewById(R.id.confirm);

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Weight.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int y, int m, int d) {
                        date.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (weight.getText().equals(""))
                    Toast.makeText(getApplicationContext(), "Uzupełnij wagę!", Toast.LENGTH_SHORT);
                else {
                    MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
                    //deleteDatabase("LSM6.db");
                    final SQLiteDatabase sqLiteDatabase = myDbHelper.getWritableDatabase();
                    final ContentValues values = new ContentValues();
                    float f = Float.valueOf(weight.getText().toString());
                    values.put("weight", f);
                    values.put("date", date.getText().toString());
                    sqLiteDatabase.insert("Weights", null, values);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }
}

