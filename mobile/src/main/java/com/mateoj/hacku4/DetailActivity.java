package com.mateoj.hacku4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView eventName = (TextView) findViewById(R.id.textView);
        eventName.setText(
                "Arb Name Dude");

        TextView eventTime = (TextView) findViewById(R.id.textView);
        eventTime.setText(
                "Arb Time Dude");

        TextView location = (TextView) findViewById(R.id.textView);
        location.setText(
                "Arb Location Dude");
    }

    public static Intent getLaunchIntent(Context context, Event event) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, event.getObjectId());

        return intent;
    }

}
