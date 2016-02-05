package com.mateoj.hacku4;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class New_Activity_A extends AppCompatActivity {

    private Button food;
    private Button sports;
    private Button academic;
    private Button nightlife;
    private Button entertainment;
    private Button misc;
    private Button nextScreen;

    private EditText eventName;
    private EditText eventDescription;

    private SeekBar timeTill;
    private SeekBar duration;

    boolean foodbool = false;
    boolean sportsbool = false;
    boolean acadbool = false;
    boolean nlbool = false;
    boolean enterbool = false;
    boolean miscbool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__activity_a);

        eventName = (EditText) findViewById(R.id.event_name);
        eventDescription = (EditText) findViewById(R.id.event_description);

        food = (Button) findViewById(R.id.food_button);
        sports = (Button) findViewById(R.id.sports_button);
        academic = (Button) findViewById(R.id.academic_button);
        nightlife = (Button) findViewById(R.id.night_life_button);
        entertainment = (Button) findViewById(R.id.entertainment_button);
        misc = (Button) findViewById(R.id.misc_button);

        nextScreen = (Button) findViewById(R.id.next_button);

        timeTill = (SeekBar) findViewById(R.id.time_till_seekBar);
        duration = (SeekBar) findViewById(R.id.duration_seekBar);

        food.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!foodbool) { foodbool = true; food.setTypeface(null, Typeface.BOLD);

                } else { foodbool = false; food.setTypeface(Typeface.DEFAULT); }
            Log.v("New_Activity_A.java", "Clicked food -- changed to " + foodbool);
            }
        });

        sports.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sportsbool) { sportsbool = true; sports.setTypeface(null, Typeface.BOLD);
                } else { sportsbool = false; sports.setTypeface(Typeface.DEFAULT); }
                Log.v("New_Activity_A.java", "Clicked sports -- changed to " + sportsbool);
            }
        });

        academic.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!acadbool) { acadbool = true; academic.setTypeface(null, Typeface.BOLD);
                } else { acadbool = false; academic.setTypeface(Typeface.DEFAULT); }
                Log.v("New_Activity_A.java", "Clicked academic -- changed to " + acadbool);
            }
        });

        nightlife.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nlbool) { nlbool = true; nightlife.setTypeface(null, Typeface.BOLD);
                } else { nlbool = false; nightlife.setTypeface(Typeface.DEFAULT); }
                Log.v("New_Activity_A.java", "Clicked nightlife -- changed to " + nlbool);
            }
        });

        entertainment.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterbool) { enterbool = true; entertainment.setTypeface(null, Typeface.BOLD);
                } else { enterbool = false; entertainment.setTypeface(Typeface.DEFAULT); }
                Log.v("New_Activity_A.java", "Clicked entertainment -- changed to " + enterbool);
            }
        });

        misc.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!miscbool) { miscbool = true; misc.setTypeface(null, Typeface.BOLD);
                } else { miscbool = false; misc.setTypeface(Typeface.DEFAULT); }
                Log.v("New_Activity_A.java", "Clicked misc -- changed to " + miscbool);
            }
        });

        nextScreen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("New_Activity_A.java", "Clicked to proceed to next page!");
                Intent i = new Intent(New_Activity_A.this, New_Activity_B.class);
                i.putExtra("myName", eventName.getText().toString());
                i.putExtra("myDescription", eventDescription.getText().toString());
                i.putExtra("time_till", timeTill.getProgress());
                i.putExtra("time_duration", duration.getProgress());

                ArrayList<String> myTags = new ArrayList<>();
                if(foodbool) { myTags.add("Food"); }
                if(sportsbool) { myTags.add("Sport"); }
                if(acadbool) { myTags.add("Academic"); }
                if(nlbool) { myTags.add("Night Life"); }
                if(enterbool) { myTags.add("Entertainment"); }
                if(miscbool) { myTags.add("Miscellaneous"); }

                i.putStringArrayListExtra("myTags", myTags);
                startActivity(i);
            }
        });


        timeTill.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView until = (TextView) findViewById(R.id.min_until);
                until.setText(Integer.toString(progress) + " minutes");
                Log.v("New_Activity_A.java", "Changed time until to " + Integer.toString(progress) + " minutes.");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView dur = (TextView) findViewById(R.id.min_long);
                dur.setText(Integer.toString(progress) + " minutes");
                Log.v("New_Activity_A.java", "Changed duration to " + Integer.toString(progress) + " minutes.");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

}
