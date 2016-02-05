package com.mateoj.hacku4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import android.content.Intent;
import android.util.Log;

public class New_Activity_B extends AppCompatActivity {

    ArrayList<String> tags = new ArrayList<>();
    String name;
    String description;
    int timeTill;
    int timeDuration;

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

    }
}
