package com.mateoj.hacku4;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FencesActivity extends LocationActivity implements ResultCallback{
    public static final String PREFS_APP = "appPreferences";
    public static final String KEY_LAUNCHED = "appLaunched";
    PendingIntent mGeofencePendingIntent;
    StringPreference firstLaunchedPref;

    public static final String TAG = FencesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fences);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        init();

    }

    private void setUpGeofences() {
        LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, getGeofencePendingIntent());

        ParseQuery<Building> query = ParseQuery.getQuery("Building");
        query.findInBackground(new FindCallback<Building>() {
            @Override
            public void done(List<Building> objects, ParseException e) {
                List<Geofence> geofences = new ArrayList<>();

                for (Building building : objects) {
                    geofences.add(buildFenceFromBuilding(building));
                }

                geofences.add(new Geofence.Builder().setRequestId("dominion")
                        .setCircularRegion(36.8475292, -76.2913321, 10)
                        .setExpirationDuration(60 * 60 * 1000)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER).build());

                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        getGeoFencingRequest(geofences),
                        getGeofencePendingIntent()
                ).setResultCallback(FencesActivity.this);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationServices.GeofencingApi.removeGeofences(mGoogleApiClient, getGeofencePendingIntent());
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    private GeofencingRequest getGeoFencingRequest(List<Geofence> geofences)
    {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofences);
        return builder.build();
    }

    private void init() {
        SharedPreferences sp = getSharedPreferences(PREFS_APP, Context.MODE_PRIVATE);
        firstLaunchedPref = new StringPreference(sp, KEY_LAUNCHED);
    }

    @Override
    public void onConnected(Bundle bundle) {
        super.onConnected(bundle);

        setUpGeofences();
    }

    private Geofence buildFenceFromBuilding(Building building) {
        return new Geofence.Builder()
                .setRequestId(building.getObjectId())
                .setCircularRegion(building.getLocation().getLatitude(),
                        building.getLocation().getLongitude(), 400)
                .setExpirationDuration(60 * 60 * 1000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }


    @Override
    public void onResult(Result result) {
        Log.d(TAG, result.toString());
    }


    @Override
    public void onLocationChanged(Location location) {

    }
}
