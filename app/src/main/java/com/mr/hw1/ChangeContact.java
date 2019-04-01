package com.mr.hw1;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Random;

public class ChangeContact extends AppCompatActivity {
    private int selected_contact = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contact);
        setSpinnerItemSelectedListener();
        Intent received_intent = getIntent();
        Integer received_contact = received_intent.getIntExtra(MainActivity.CONTACT_ID, 0);
        ((Spinner) findViewById(R.id.spinner)).setSelection(received_contact);
    }


    public void setSpinnerItemSelectedListener(){
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        if(spinner != null){
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected_contact = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }
    public void setContactClick(View v){
        Intent data = new Intent();
        data.putExtra(MainActivity.CONTACT_ID,selected_contact);
        setResult(RESULT_OK, data);
        finish();
    }

    public void setCancelClick(View v){
        //Intent data = new Intent();
        //setResult(RESULT_CANCELED, data);
        //finish();
        onBackPressed();
    }
}
