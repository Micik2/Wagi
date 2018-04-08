package com.example.maciek.wagi;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Maciek on 2017-11-13.
 */

public class Weights extends AppCompatActivity {
    private View view;
    private LinearLayout linearLayout;
    private Button submit;
    private CheckBox checkBox;
    private MyDbHelper myDbHelper;
    //private SQLiteDatabase database;
    private Cursor cursor;
    private EditText weightL;
    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weights);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        myDbHelper = new MyDbHelper(getApplicationContext());
        SQLiteDatabase database = myDbHelper.getReadableDatabase();
        String[] tableColumns = new String[] {"Weight", "Date"};
        //String whereClause = "Password = ?";
        //String[] whereArgs = new String[] {pass};
        cursor = database.query("Weights", tableColumns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            System.out.println("Kursor tu jest");
            //int i = 0;
            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                //System.out.println("Kursor 0: " + cursor.getString(0));
                //.out.println("Kursor 1: " + cursor.getString(1));
                String recordWeight = cursor.getString(0);
                System.out.println("Z bazy waga " + recordWeight);
                String recordDate = cursor.getString(1);
                System.out.println("Z bazy data " + recordDate);
                //String record = cursor.getString(i);
                linearLayout = (LinearLayout) findViewById(R.id.layout);


                view = getLayoutInflater().inflate(R.layout.layout, null);
                linearLayout.addView(view);
                TextView weight = (TextView) view.findViewById(R.id.weightL);
                TextView date = (TextView) view.findViewById(R.id.dateL);
                weight.setText(recordWeight);
                //weight.setHint(recordWeight);
                //weight.setFilters(new InputFilter[] {new Weight.Filter()});
                date.setText(recordDate);
                //++;
            }
            //database.close();
        }
        else
            Toast.makeText(getApplicationContext(), "Brak ważeń!", Toast.LENGTH_SHORT);


        /*View[] views = new View[10];
        //Button[] buttons = new Button[10];
        for (int j = 0; j < linearLayout.getChildCount(); j++) {
            views[j] = linearLayout.getChildAt(j);
            //buttons = views[j].findViewById(R.id.submit);
        }*/



        //submit = (Button) findViewById(R.id.submit);
        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 1; i <= linearLayout.getChildCount(); i++) {
                    View child = linearLayout.getChildAt(i);
                    System.out.println("Child: " + child);
                    submit = child.findViewById(R.id.submit);
                    //checkBox = child.findViewById(R.id.check);
                    //if (checkBox.isChecked()) {
                    //    linearLayout.removeView(child);
                    //}
                }
            }
        });*/
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        onCreate();
    }*/

    /*public void change(View view) {
        LinearLayout lay = (LinearLayout) view.getParent();
        TextView l = lay.findViewById(R.id.weightL);
        String lText = l.getText().toString();
        //TextView d = lay.findViewById(R.id.dateL);
        //String dText = d.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("weight", lText);
        database.update("Weights", contentValues, "Weight = ?", new String[] {lTextL, dTextL});
    }*/

    public void remove(View view) {
        //ViewParent rec = view.getParent();
        //SQLiteDatabase db = getApplicationContext().getWritableDatabase();
        LinearLayout lay = (LinearLayout) view.getParent();
        TextView l = lay.findViewById(R.id.weightL);
        String lText = l.getText().toString();
        System.out.println("Waga = " + lText);
        Log.d("WEIGHTS","Waga = " + lText);
        //TextView d = lay.findViewById(R.id.dateL);
        //String dText = d.getText().toString();
        //System.out.println("Data: " + dText);
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());

        // Define 'where' part of query.
        String selection = "Weight" + " = ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { lText };
// Issue SQL statement.
        SQLiteDatabase db = myDbHelper.getWritableDatabase();
//        db.delete("Weights", selection, selectionArgs);
        db.delete("Weights", "Weight = ?", new String[] {lText});

        // SQLiteDatabase database1 = myDbHelper.getWritableDatabase();
        //database.delete("Weights", "Weight = "+ lText +  " and Date = " + dText, null);
        //database1.execSQL("DELETE from Weights where Weight = '" + lText + "'");
        //database.close();

        //database.delete("Weights", "Weight = ?", new String[] {lText});
        lay.removeAllViews();

        /*TextView l = (TextView) view.findViewById(R.id.weightL);
        String lText = l.getText().toString();
        TextView d = (TextView) view.findViewById(R.id.dateL);
        String dText = d.getText().toString();
        database.delete("Weights", "Weight = " + lText + "and" + "Date = " + dText, null);
        startActivity(new Intent(getApplicationContext(), Weights.class));*/
        //View view1 = linearLayout.findViewById(R.id.lay);
        //view1.setVisibility(View.GONE);

        //linearLayout.removeView(view);
    }
}
