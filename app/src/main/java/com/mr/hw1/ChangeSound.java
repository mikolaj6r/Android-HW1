package com.mr.hw1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ChangeSound extends AppCompatActivity {
    private int selected_sound = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_sound);
        Intent received_intent = getIntent();
        Integer sound_id =
                received_intent.getIntExtra(MainActivity.SOUND_ID,0);

        ((RadioButton)((RadioGroup) findViewById(R.id.radioGroup)).getChildAt(sound_id)).setChecked(true);

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked) {
            RadioGroup group = (RadioGroup) findViewById(R.id.radioGroup);
            int idx = group.indexOfChild(view);
            selected_sound = idx;
        }
    }
    public void setSoundClick(View v){
        Intent data = new Intent();
        data.putExtra(MainActivity.SOUND_ID,selected_sound);
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
