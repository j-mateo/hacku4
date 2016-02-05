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
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT_ID = "eventId";
    @Bind(R.id.textView)
    TextView title;

    @Bind(R.id.textView2)
    TextView subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
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

        if (getIntent().hasExtra(EXTRA_EVENT_ID)) {
            String objectId = getIntent().getStringExtra(EXTRA_EVENT_ID);
            fetchEvent(objectId);
        } else {
            throw new IllegalArgumentException("Must instantiate using the getLaunchIntent");
        }
    }

    private void fetchEvent(String objectId) {
        ParseQuery.getQuery(Event.class)
                .getInBackground(objectId, new GetCallback<Event>() {
                    @Override
                    public void done(Event object, ParseException e) {
                        if (e != null) {
                            Toast.makeText(DetailActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            initEvent(object);
                        }
                    }
                });
    }

    private void initEvent(Event event) {
        title.setText(event.getName());
        subtitle.setText(event.getEnd().toString("HH:mm", Locale.US));
    }

    public static Intent getLaunchIntent(Context context, Event event) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, event.getObjectId());

        return intent;
    }

}
