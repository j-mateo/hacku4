package com.mateoj.hacku4;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.joda.time.DateTime;

public class New_Activity_B extends AppCompatActivity {

    ArrayList<String> tags = new ArrayList<>();
    String name;
    String description;
    int timeTill;
    int timeDuration;
    Button nextScreen;
    private GoogleMap eventMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__activity__b);

        Intent oldIntent = getIntent();
        timeTill = oldIntent.getIntExtra("time_till", 60);
        timeDuration = oldIntent.getIntExtra("time_duration", 120);
        name = oldIntent.getStringExtra("myName");
        description = oldIntent.getStringExtra("myDescription");
        tags = oldIntent.getStringArrayListExtra("myTags");

        Log.v("New_Activity_B.java", "Name: " + name);
        Log.v("New_Activity_B.java", "Name: " + description);
        Log.v("New_Activity_B.java", "Time Till: " + Integer.toString(timeTill));
        Log.v("New_Activity_B.java", "Duration: " + Integer.toString(timeDuration));

        for(int i = 0; i < tags.size(); i++) {
            Log.v("New_Activity_B.java", "Tag " + Integer.toString(i) + " " + tags.get(i));
        }


        nextScreen = (Button) findViewById(R.id.event_creation_button);
        nextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("New_Activity_B.java", "Yes hello.");
                Event newEvent = new Event();
                newEvent.setName(name);
                newEvent.setDescription(description);
                DateTime timeNow = new DateTime();
                newEvent.setStart(timeNow.plusMinutes(timeTill));
                newEvent.setEnd(timeNow.plusMinutes(timeTill + timeDuration));

            }
        });


    }
   // @Override
//    protected void onResume() {
//        super.onResume();
//        mapFragment = new MainMapFragment();
//
//        SupportMapFragment mapFrag=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment2);
//
//        mapFragment.placeMarker(mapFrag);
//    }
//    public void onMapReady() {
//
//    }




}
