package com.mateoj.hacku4;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends LocationActivity implements MyRecyclerViewAdapter.MyClickListener,
        ResultCallback {

    public static String TAG = MainActivity.class.getSimpleName();
    public static final String PREFS_APP = "appPreferences";
    public static final String KEY_LAUNCHED = "appLaunched";
    private PendingIntent mGeofencePendingIntent;
    private StringPreference firstLaunchedPref;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton createEvent;
    private boolean isQueryInProgress = false;
    private boolean needsData = true;
    private String mSort = "near";
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initSortSpinner();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        createEvent = (FloatingActionButton) findViewById(R.id.fab2);
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, New_Activity_A.class);
                startActivity(i);
            }
        });

        init();
    }

    private Geofence buildFenceFromBuilding(Building building) {
        return new Geofence.Builder()
                .setRequestId(building.getObjectId())
                .setCircularRegion(building.getLocation().getLatitude(),
                        building.getLocation().getLongitude(), 100)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }


    @Override
    public void onResult(Result result) {
        Log.d(TAG, result.toString());
    }

    private void initSortSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSort = ((String) adapterView.getItemAtPosition(i));
                Toast toast = Toast.makeText(getApplicationContext(), "Item: " + Integer.toString(i) + ", " + mSort, Toast.LENGTH_LONG);
                toast.show();
                executeQuery();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });
    }
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        mGeofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);

        return mGeofencePendingIntent;
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
        if (!firstLaunchedPref.isSet()) {
            setUpGeofences();
            firstLaunchedPref.set("yes");
        }
    }
    private void setUpGeofences() {
        ParseQuery<Building> query = ParseQuery.getQuery("Building");
        query.findInBackground(new FindCallback<Building>() {
            @Override
            public void done(List<Building> objects, ParseException e) {
                List<Geofence> geofences = new ArrayList<>();

                for (Building building : objects) {
                    geofences.add(buildFenceFromBuilding(building));
                }
                LocationServices.GeofencingApi.addGeofences(
                        mGoogleApiClient,
                        getGeoFencingRequest(geofences),
                        getGeofencePendingIntent()
                ).setResultCallback(MainActivity.this);
            }
        });
    }



    private ArrayList<Event> getDataSet() {
        ArrayList results = new ArrayList<Event>();
        return results;
    }

    @Override
    public void onItemClick(int position, View v) {
        startActivity(DetailActivity.getLaunchIntent(this, mAdapter.getItem(position)));
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (isQueryInProgress || !needsData)
            return;

        executeQuery();
    }

    private void executeQuery() {
        if (mLastLocation == null)
            return;


        ParseQuery<Event> eventQuery = ParseQuery.getQuery(Event.class);
//        eventQuery.whereEqualTo("Location", building);
        eventQuery.whereEqualTo("Tags",mSort);

        org.joda.time.DateTime zulu = new DateTime(new Date()).toDateTime( org.joda.time.DateTimeZone.UTC );

        eventQuery.whereGreaterThan("EndTime", zulu.toDate());
        eventQuery.orderByAscending("Time");
        eventQuery.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> objects, ParseException e) {
                Log.d("Event", objects.toString());
                mAdapter.addAll(objects);
            }
        });


//        ParseQuery<Building> buildingQuery = ParseQuery.getQuery(Building.class);
//
//        buildingQuery.whereNear(Building.KEY_LOCATION, new ParseGeoPoint(mLastLocation.getLatitude(),
//                mLastLocation.getLongitude()));
//        isQueryInProgress = true;
//
////        if (mSort.equals("upcoming")) {
////            buildingQuery.orderByAscending("Time");
////        } else {
////            buildingQuery.orderByAscending("Location");
////        }
//
//
//
//        buildingQuery.findInBackground(new FindCallback<Building>() {
//            @Override
//            public void done(List<Building> objects, ParseException e) {
//                mAdapter.clear();
//                needsData = false;
//                isQueryInProgress = false;
//                for (Building building : objects) {
//                    ParseQuery<Event> eventQuery = ParseQuery.getQuery(Event.class);
//                    eventQuery.whereEqualTo("Location", building);
//                    eventQuery.whereEqualTo("Tags",mSort);
//                    eventQuery.findInBackground(new FindCallback<Event>() {
//                        @Override
//                        public void done(List<Event> objects, ParseException e) {
//                            Log.d("Event", objects.toString());
//                            mAdapter.addAll(objects);
//                        }
//                    });
//                }
//            }
//        });
    }
}