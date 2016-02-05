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
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.json.JSONException;
import org.json.JSONObject;
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
    private Button submitButton;
    ParseGeoPoint mPlace;

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

    DateTime timeNow;

    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                mPlace = new ParseGeoPoint(place.getLatLng().latitude, place.getLatLng().longitude);
                String toastMsg = String.format("Place: %s", place.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__activity_a);
        getSupportActionBar().setTitle("Make an Event");

        eventName = (EditText) findViewById(R.id.event_name);
        eventDescription = (EditText) findViewById(R.id.event_description);

        food = (Button) findViewById(R.id.food_button);
        sports = (Button) findViewById(R.id.sports_button);
        academic = (Button) findViewById(R.id.academic_button);
        nightlife = (Button) findViewById(R.id.night_life_button);
        entertainment = (Button) findViewById(R.id.entertainment_button);
        misc = (Button) findViewById(R.id.misc_button);
        submitButton = (Button) findViewById(R.id.submitButton);

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

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(New_Activity_A.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
//                Log.v("New_Activity_A.java", "Clicked to proceed to next page!");
//                Intent i = new Intent(New_Activity_A.this, New_Activity_B.class);
//                i.putExtra("myName", eventName.getText().toString());
//                i.putExtra("myDescription", eventDescription.getText().toString());
//                i.putExtra("time_till", timeTill.getProgress());
//                i.putExtra("time_duration", duration.getProgress());
//
//                ArrayList<String> myTags = new ArrayList<>();
//                if(foodbool) { myTags.add("Food"); }
//                if(sportsbool) { myTags.add("Sport"); }
//                if(acadbool) { myTags.add("Academic"); }
//                if(nlbool) { myTags.add("Night Life"); }
//                if(enterbool) { myTags.add("Entertainment"); }
//                if(miscbool) { myTags.add("Miscellaneous"); }
//
//                i.putStringArrayListExtra("myTags", myTags);
//                startActivity(i);
            }
        });

        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("New_Activity_B.java", "Yes hello.");
                final Event newEvent = new Event();
                newEvent.setName(eventName.getText().toString());
                newEvent.setDescription(eventDescription.getText().toString());
                DateTime timeNow = new DateTime();
                newEvent.setStart(timeNow.plusMinutes(timeTill.getProgress()));
                newEvent.setEnd(timeNow.plusMinutes(timeTill.getProgress() + duration.getProgress()));
                if(foodbool) { newEvent.setTag("Food"); }
                if(sportsbool) { newEvent.setTag("Sport"); }
                if(acadbool) { newEvent.setTag("Academic"); }
                if(nlbool) { newEvent.setTag("NightLife"); }
                if(enterbool) { newEvent.setTag("Entertainment"); }
                if(miscbool) { newEvent.setTag("Miscellaneous"); }
                newEvent.setLocation(mPlace);
                newEvent.setOwner(ParseUser.getCurrentUser());
                newEvent.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            ParsePush push = new ParsePush();
                            push.setChannel(newEvent.getTag());
                            push.setData(getPushData(newEvent));
                            push.sendInBackground();
                        }
                    }
                });
                Toast.makeText(New_Activity_A.this, "Event Created!", Toast.LENGTH_LONG).show();
                finish();
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
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private JSONObject getPushData(Event event) {
        JSONObject jsonObject = new JSONObject();
        try {
            String str = "New event \"";
            str += event.getName();
            str += "\" in ";
            timeNow = new DateTime();
            Minutes timeBegin = Minutes.minutesBetween(timeNow,event.getStart());
            str += Integer.toString(timeBegin.getMinutes());
            str += " minutes!";
            jsonObject.put("eventId", event.getObjectId());
            jsonObject.put("alert", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
