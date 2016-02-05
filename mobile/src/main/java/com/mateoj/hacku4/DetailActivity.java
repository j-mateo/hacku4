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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String EXTRA_EVENT_ID = "eventId";
    @Bind(R.id.textView)
    TextView title;

    @Bind(R.id.textView2)
    TextView subtitle;

    private Event mEvent;
    private boolean isMapReady = false;
    private GoogleMap mGoogleMap;

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

            mEvent.add("UsersGoing", ParseUser.getCurrentUser());
            }
        });

        if (getIntent().hasExtra(EXTRA_EVENT_ID)) {
            String objectId = getIntent().getStringExtra(EXTRA_EVENT_ID);
            fetchEvent(objectId);
        } else {
            throw new IllegalArgumentException("Must instantiate using the getLaunchIntent");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

    }

    private void fetchEvent(String objectId) {
        ParseQuery.getQuery(Event.class)
                .include("Location")
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
        mEvent = event;
        title.setText(event.getName());
        subtitle.setText(event.getEnd().toString("HH:mm", Locale.US));
        if (isMapReady) {
            moveCameraToLocation();
        }

    }

    private void moveCameraToLocation() {
        ParseGeoPoint geoPoint;
        String title;
        if (mEvent.getLocation() == null) {
            geoPoint = mEvent.getParseGeoPoint("UserLocation");
            title = mEvent.getName();
        } else {
            geoPoint = mEvent.getLocation().getLocation();
            title = mEvent.getLocation().getName();
        }

        LatLng location = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        mGoogleMap.addMarker(new MarkerOptions().position(location).title(title));
    }

    public static Intent getLaunchIntent(Context context, Event event) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, event.getObjectId());

        return intent;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        isMapReady = true;
        mGoogleMap = googleMap;
        if (mEvent != null) {
            moveCameraToLocation();
        }
    }
}
